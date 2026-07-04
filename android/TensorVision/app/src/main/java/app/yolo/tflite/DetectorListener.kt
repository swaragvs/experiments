// DetectorListener.kt
package app.yolo.tflite

interface DetectorListener {
    fun onResults(results: List<DetectionResult>, inferenceTime: Long)
    fun onError(error: String)
}