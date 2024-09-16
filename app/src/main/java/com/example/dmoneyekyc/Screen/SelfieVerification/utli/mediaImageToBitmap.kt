package com.example.dmoneyekyc.Screen.SelfieVerification.utli

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.YuvImage
import android.graphics.Rect
import java.io.ByteArrayOutputStream
import android.media.Image

fun mediaImageToBitmap(mediaImage: Image): Bitmap? {
    if (mediaImage.format != ImageFormat.YUV_420_888) {
        return null
    }
    
    val yBuffer = mediaImage.planes[0].buffer // Y
    val uBuffer = mediaImage.planes[1].buffer // U
    val vBuffer = mediaImage.planes[2].buffer // V

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    // U and V are swapped
    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, mediaImage.width, mediaImage.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, mediaImage.width, mediaImage.height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}
