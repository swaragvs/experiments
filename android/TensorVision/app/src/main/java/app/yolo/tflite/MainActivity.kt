@file:OptIn(androidx.camera.core.ExperimentalGetImage::class)
package app.yolo.tflite

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.yolo.tflite.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity(), DetectorListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraExecutor: ExecutorService
    private var useFrontCamera = false
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var yuvConverter: YuvToRgbConverter

    companion object {
        private const val TAG = "YOLO-TFLITE"
        private const val REQUEST_CAMERA_PERMISSION = 10
        private const val MODEL_FILE = "yolov8n_float32.tflite"
        private const val LABELS_FILE = "coco_labels.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()
        yuvConverter = YuvToRgbConverter(this)

        // Initialize TFLite bridge
        TFLiteHelper.init(this, this, MODEL_FILE, LABELS_FILE)

        if (allPermissionsGranted()) startCamera() else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)

        binding.btnCamera.setOnClickListener {
            if (cameraProvider != null) {
                cameraProvider?.unbindAll()
                cameraProvider = null
                binding.btnCamera.text = "Resume"
            } else {
                startCamera()
                binding.btnCamera.text = "Pause"
            }
        }

        binding.btnSwitchCamera.setOnClickListener {
            useFrontCamera = !useFrontCamera
            startCamera()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                cameraProvider?.unbindAll()

                val cameraSelector = if (useFrontCamera) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA

                val preview = Preview.Builder().build().also { it.setSurfaceProvider(binding.previewView.surfaceProvider) }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                    .build()

                imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                    val image = imageProxy.image
                    if (image != null) {
                        try {
                            val bmp = Bitmap.createBitmap(imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888)
                            yuvConverter.yuvToRgb(image, bmp)

                            // If front-camera, mirror horizontally
                            val finalBmp = if (useFrontCamera) {
                                val matrix = Matrix().apply { preScale(-1f, 1f) }
                                Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, false)
                            } else bmp

                            TFLiteHelper.detect(finalBmp)
                        } catch (e: Exception) {
                            Log.e(TAG, "Analyzer error: ${e.message}", e)
                        }
                    }
                    imageProxy.close()
                }

                cameraProvider?.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
                binding.btnCamera.text = "Pause"

            } catch (e: Exception) {
                Log.e(TAG, "Camera start failed: ${e.message}", e)
                Toast.makeText(this, "Failed to start camera: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (allPermissionsGranted()) startCamera()
            else {
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onResults(results: List<DetectionResult>, inferenceTime: Long) {
        runOnUiThread {
            binding.tvStatus.text = "Detections: ${results.size} | Time: ${inferenceTime}ms"
            binding.overlay.setResults(results)
        }
    }

    override fun onError(error: String) {
        runOnUiThread {
            binding.tvStatus.text = "Error: $error"
            Log.e(TAG, error)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        TFLiteHelper.close()
    }
}