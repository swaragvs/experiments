// YoloV8Classifier.kt
package app.yolo.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.SystemClock
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max
import kotlin.math.min

class YoloV8Classifier(
    private val context: Context,
    private val modelPath: String,
    private val labelPath: String,
    private val listener: DetectorListener,
    private val confidenceThreshold: Float = 0.6f,
    private val iouThreshold: Float = 0.5f
) {

    private var interpreter: Interpreter? = null
    private val labels = mutableListOf<String>()
    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numClasses = 0
    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(0f, 255f))
        .add(CastOp(DataType.FLOAT32))
        .build()

    init {
        setup()
    }

    private fun setup() {
        try {
            val compatList = CompatibilityList()
            val options = Interpreter.Options()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegateOptions = compatList.bestOptionsForThisDevice
                options.addDelegate(GpuDelegate(delegateOptions))
                Log.i("YoloV8Classifier", "GPU Delegate enabled")
            } else {
                options.setNumThreads(4)
                Log.i("YoloV8Classifier", "Using CPU: threads=4")
            }

            val model = FileUtil.loadMappedFile(context, modelPath)
            interpreter = Interpreter(model, options)

            // Read input tensor shape safely
            val inputTensor = interpreter!!.getInputTensor(0)
            val inputShape = inputTensor.shape() // [1, h, w, c]
            tensorHeight = inputShape[1]
            tensorWidth = inputShape[2]

            // Read output shape and compute num classes robustly
            val outputTensor = interpreter!!.getOutputTensor(0)
            val outputShape = outputTensor.shape() // could be [1, 4+num_classes, N] or [1, N, 4+num_classes]

            // Infer which axis is the 'channels' axis (4 + num_classes)
            if (outputShape.size >= 3) {
                // Find index with value >= 5 which likely matches (4 + numClasses)
                val candidateIndex = outputShape.indexOfFirst { it >= 5 }
                if (candidateIndex >= 0 && candidateIndex < outputShape.size) {
                    val channels = outputShape[candidateIndex]
                    numClasses = channels - 4
                } else {
                    // Fallback: try to assume [1, N, 4+numClasses]
                    numClasses = outputShape.last() - 4
                }
            }

            Log.i("YoloV8Classifier", "Input shape: ${inputShape.joinToString()}")
            Log.i("YoloV8Classifier", "Output shape: ${outputShape.joinToString()}")
            Log.i("YoloV8Classifier", "Number of classes inferred: $numClasses")

            // Load labels
            val reader = BufferedReader(InputStreamReader(context.assets.open(labelPath)))
            reader.useLines { lines -> lines.forEach { labels.add(it) } }

        } catch (e: Exception) {
            val error = "Classifier setup failed: ${e.message}"
            Log.e("YoloV8Classifier", error, e)
            listener.onError(error)
        }
    }

    fun detect(frame: Bitmap) {
        if (interpreter == null) return

        val start = SystemClock.uptimeMillis()

        val resized = Bitmap.createScaledBitmap(frame, tensorWidth, tensorHeight, true)
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resized)
        val processed = imageProcessor.process(tensorImage)

        val outputTensor = interpreter!!.getOutputTensor(0)
        val outputBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())

        interpreter!!.run(processed.buffer, outputBuffer.buffer.rewind())

        val results = postProcess(outputBuffer.floatArray)
        val inferenceTime = SystemClock.uptimeMillis() - start
        listener.onResults(results, inferenceTime)
    }

    private fun postProcess(outputArray: FloatArray): List<DetectionResult> {
        val results = mutableListOf<DetectionResult>()

        // Determine numBoxes and channels reliably
        val outShape = interpreter!!.getOutputTensor(0).shape()
        val channelsAxis = outShape.indexOfFirst { it >= 5 } // 4 + numClasses
        val numBoxesAxis = if (channelsAxis == 1) 2 else 1
        val numBoxes = outShape[numBoxesAxis]
        val channels = outShape[channelsAxis]

        // transpose logic: create 2D array [numBoxes][channels]
        val transposed = Array(numBoxes) { FloatArray(channels) }
        for (i in 0 until channels) {
            for (j in 0 until numBoxes) {
                // mapping depends on axis order
                val index = if (channelsAxis == 1) i * numBoxes + j else j * channels + i
                transposed[j][i] = outputArray[index]
            }
        }

        for (row in transposed) {
            val cx = row[0]
            val cy = row[1]
            val w = row[2]
            val h = row[3]

            var maxConf = 0f
            var maxCls = -1
            for (i in 4 until row.size) {
                if (row[i] > maxConf) {
                    maxConf = row[i]
                    maxCls = i - 4
                }
            }

            if (maxConf < confidenceThreshold) continue

            val x1 = cx - w / 2
            val y1 = cy - h / 2
            val x2 = cx + w / 2
            val y2 = cy + h / 2

            val label = if (maxCls >= 0 && maxCls < labels.size) labels[maxCls] else "Class_$maxCls"

            results.add(DetectionResult(RectF(x1, y1, x2, y2), label, maxConf))
        }

        val nms = nonMaxSuppression(results, iouThreshold)

        // Return results scaled to [0, 1]
        return nms.map { det ->
            det.copy(
                boundingBox = RectF(
                    det.boundingBox.left / tensorWidth,
                    det.boundingBox.top / tensorHeight,
                    det.boundingBox.right / tensorWidth,
                    det.boundingBox.bottom / tensorHeight
                )
            )
        }
    }

    private fun nonMaxSuppression(detections: List<DetectionResult>, iouThreshold: Float): List<DetectionResult> {
        val sorted = detections.sortedByDescending { it.confidence }
        val selected = mutableListOf<DetectionResult>()
        val active = BooleanArray(sorted.size) { true }

        for (i in sorted.indices) {
            if (!active[i]) continue
            selected.add(sorted[i])
            for (j in i + 1 until sorted.size) {
                if (!active[j]) continue
                val iou = calculateIoU(sorted[i].boundingBox, sorted[j].boundingBox)
                if (iou > iouThreshold) active[j] = false
            }
        }
        return selected
    }

    private fun calculateIoU(a: RectF, b: RectF): Float {
        val xA = max(a.left, b.left)
        val yA = max(a.top, b.top)
        val xB = min(a.right, b.right)
        val yB = min(a.bottom, b.bottom)

        val inter = max(0f, xB - xA) * max(0f, yB - yA)
        val areaA = (a.right - a.left) * (a.bottom - a.top)
        val areaB = (b.right - b.left) * (b.bottom - b.top)
        val union = areaA + areaB - inter
        return if (union > 0f) inter / union else 0f
    }

    fun close() {
        try {
            interpreter?.close()
            interpreter = null
        } catch (e: Exception) {
            Log.e("YoloV8Classifier", "Error closing interpreter: ${e.message}", e)
        }
    }
}