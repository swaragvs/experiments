// TFLiteHelper.kt
package app.yolo.tflite

import android.content.Context
import android.graphics.Bitmap

/**
 * Singleton bridge to centralize model lifecycle and expose a clean API to UI layer.
 */
object TFLiteHelper {
    private var classifier: YoloV8Classifier? = null

    fun init(context: Context, listener: DetectorListener, modelPath: String = "yolov8n_float32.tflite", labelPath: String = "coco_labels.txt") {
        if (classifier == null) {
            classifier = YoloV8Classifier(context.applicationContext, modelPath, labelPath, listener)
        }
    }

    fun detect(bitmap: Bitmap) {
        classifier?.detect(bitmap)
    }

    fun close() {
        classifier?.close()
        classifier = null
    }
}