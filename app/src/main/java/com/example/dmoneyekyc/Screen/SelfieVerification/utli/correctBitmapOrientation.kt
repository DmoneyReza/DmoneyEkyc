package com.example.dmoneyekyc.Screen.SelfieVerification.utli

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.InputStream

fun correctBitmapOrientation(context: Context, imageUri: android.net.Uri, bitmap: Bitmap): Bitmap {
    val contentResolver: ContentResolver = context.contentResolver

    try {
        // Open input stream to read EXIF metadata
        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
        inputStream?.use {
            val exif = ExifInterface(it)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()
            when (orientation) {
                0 -> matrix.postRotate(-90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
            }

            // Return the rotated bitmap
            return Bitmap.createBitmap(
                bitmap,
                0, 0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // Return the original bitmap if no rotation is needed or an error occurs
    return bitmap
}
