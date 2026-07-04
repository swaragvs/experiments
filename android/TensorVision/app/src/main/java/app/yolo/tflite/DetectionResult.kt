// DetectionResult.kt
package app.yolo.tflite

import android.graphics.RectF

data class DetectionResult(
    val boundingBox: RectF,
    val label: String,
    val confidence: Float
)