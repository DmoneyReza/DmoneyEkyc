package com.example.imagetotextextractor.utlis.OCR

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.google.mlkit.vision.common.InputImage

fun convertImageBitmapToInputImage(imageBitmap: ImageBitmap): InputImage {
    val bitmap = imageBitmap.asAndroidBitmap()
    return InputImage.fromBitmap(bitmap, 0)
}