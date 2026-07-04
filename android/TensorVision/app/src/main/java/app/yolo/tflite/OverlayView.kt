// OverlayView.kt
package app.yolo.tflite

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.abs

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boxColors = listOf(
        ContextCompat.getColor(context, android.R.color.holo_red_dark),
        ContextCompat.getColor(context, android.R.color.holo_blue_dark),
        ContextCompat.getColor(context, android.R.color.holo_orange_dark),
        ContextCompat.getColor(context, android.R.color.holo_purple),
        ContextCompat.getColor(context, android.R.color.holo_green_dark),
        ContextCompat.getColor(context, android.R.color.holo_red_light)
    )

    private val boxPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 45f
        isAntiAlias = true
    }

    private val textBackgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        alpha = 200
    }

    @Volatile
    private var results: List<DetectionResult> = emptyList()

    fun setResults(list: List<DetectionResult>) {
        results = list
        postInvalidateOnAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        results.forEach { result ->
            val b = result.boundingBox
            val left = b.left * width
            val top = b.top * height
            val right = b.right * width
            val bottom = b.bottom * height

            val color = getColorForLabel(result.label)
            boxPaint.color = color
            textBackgroundPaint.color = color

            canvas.drawRect(left, top, right, bottom, boxPaint)

            val drawableText = "${result.label} ${"%.2f".format(result.confidence)}"
            val textBounds = android.graphics.Rect()
            textPaint.getTextBounds(drawableText, 0, drawableText.length, textBounds)
            val textHeight = textBounds.height().toFloat()
            val textWidth = textBounds.width().toFloat()

            var bgTop = top - textHeight - 16f
            if (bgTop < 0f) bgTop = top + 8f
            val bgRect = RectF(left, bgTop, left + textWidth + 24f, bgTop + textHeight + 24f)
            canvas.drawRect(bgRect, textBackgroundPaint)
            canvas.drawText(drawableText, left + 12f, bgRect.bottom - 10f, textPaint)
        }
    }

    private fun getColorForLabel(label: String): Int {
        val idx = abs(label.hashCode()) % boxColors.size
        return boxColors[idx]
    }
}