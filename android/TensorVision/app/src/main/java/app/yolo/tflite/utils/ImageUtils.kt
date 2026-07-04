package app.yolo.tflite.utils

import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

object ImageUtils {
    fun yuvToRgbByteBuffer(image: ImageProxy, inputWidth: Int, inputHeight: Int): ByteBuffer {
        // TODO: Implement conversion YUV420_888 -> RGB ByteBuffer, resize to model input
        // There are multiple implementations online. Keep memory allocations minimal.
        throw NotImplementedError("Implement YUV -> RGB conversion")
    }
}
