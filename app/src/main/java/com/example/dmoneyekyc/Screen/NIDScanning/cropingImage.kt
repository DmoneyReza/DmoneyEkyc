package com.example.imagetotextextractor.utlis.working

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap


fun drawAndCropRectangleOnImageBitmap(imageBitmap: ImageBitmap): ImageBitmap {
    // Convert ImageBitmap to Android Bitmap
    val bitmap = imageBitmap.asAndroidBitmap()


    // Calculate rectangle width and height based on image dimensions
    val rectangleWidth = (imageBitmap.width * 0.60).toInt()
    val rectangleHeight =(imageBitmap.height * 0.30).toInt()
    val left = (bitmap.width - rectangleWidth) / 2
    val top = (bitmap.height - rectangleHeight) / 2
    val right = left + rectangleWidth
    val bottom = top + rectangleHeight

    // Create a new Bitmap for cropped image
    val croppedBitmap = Bitmap.createBitmap(bitmap, left, top, rectangleWidth, rectangleHeight)

    return croppedBitmap.asImageBitmap()
}
