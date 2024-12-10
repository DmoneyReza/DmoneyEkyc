package com.example.dmoneyekyc.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun saveBitmapToStorage(context: Context, bitmap: Bitmap, fileName: String = "image_${System.currentTimeMillis()}.jpg"): Boolean {
    val outputStream: OutputStream?

    // Check the API level for saving to storage
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Use MediaStore for Android 10+
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // Saves to Pictures folder
        }
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        outputStream = imageUri?.let { contentResolver.openOutputStream(it) }
    } else {
        // Save to external storage for older versions
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, fileName)
        outputStream = FileOutputStream(imageFile)
    }

    return try {
        // Compress the bitmap and write to output stream
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        outputStream?.flush()
        outputStream?.close()
        true // Success
    } catch (e: Exception) {
        e.printStackTrace()
        false // Failure
    }
}
