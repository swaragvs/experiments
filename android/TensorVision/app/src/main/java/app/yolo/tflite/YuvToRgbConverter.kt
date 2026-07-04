// YuvToRgbConverter.kt
package app.yolo.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.media.Image
import android.renderscript.*
import java.nio.ByteBuffer

/**
 * High-performance YUV_420_888 -> ARGB_8888 converter using RenderScript.
 * NOTE: RenderScript is deprecated in API 31+, but it remains an effective cross-device
 * converter on many phones. Keep this implementation for compatibility. In future, consider
 * migrating to GPU shaders or hardware-specific APIs for newest SDKs.
 */
class YuvToRgbConverter(context: Context) {
    private val rs: RenderScript = RenderScript.create(context)
    private val yuvToRgbIntrinsic: ScriptIntrinsicYuvToRGB = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs))
    private var inputAllocation: Allocation? = null
    private var outputAllocation: Allocation? = null
    private var yuvBuffer: ByteArray? = null

    @Synchronized
    fun yuvToRgb(image: Image, output: Bitmap) {
        check(image.format == ImageFormat.YUV_420_888) { "Unsupported image format" }

        val yPlane = image.planes[0].buffer
        val uPlane = image.planes[1].buffer
        val vPlane = image.planes[2].buffer

        val ySize = yPlane.remaining()
        val uSize = uPlane.remaining()
        val vSize = vPlane.remaining()
        val total = ySize + uSize + vSize

        if (yuvBuffer == null || yuvBuffer!!.size != total) {
            yuvBuffer = ByteArray(total)
        }

        // The ScriptIntrinsicYuvToRGB implementation expects the YUV data to be in a single
        // contiguous byte array, in NV21 format (Y plane, followed by interleaved VU plane).
        // YUV_420_888 planes are guaranteed to be contiguous, but the U and V planes might have
        // padding. We copy the planes into a single buffer to ensure the format is correct.
        yPlane.get(yuvBuffer!!, 0, ySize)
        vPlane.get(yuvBuffer!!, ySize, vSize)
        uPlane.get(yuvBuffer!!, ySize + vSize, uSize)

        if (inputAllocation == null) {
            val yuvType = Type.Builder(rs, Element.U8(rs)).setX(total)
            inputAllocation = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT)
        }

        if (outputAllocation == null) {
            outputAllocation = Allocation.createFromBitmap(rs, output)
        }

        inputAllocation!!.copyFrom(yuvBuffer)
        yuvToRgbIntrinsic.setInput(inputAllocation)
        yuvToRgbIntrinsic.forEach(outputAllocation)
        outputAllocation!!.copyTo(output)
    }
}