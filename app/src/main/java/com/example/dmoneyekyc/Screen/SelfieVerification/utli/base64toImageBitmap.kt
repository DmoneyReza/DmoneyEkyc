package com.example.dmoneyekyc.Screen.SelfieVerification.utli
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun base64ToImageBitmap(base64String: String): ImageBitmap? {
    return try {
        // Step 1: Decode the Base64 string
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

        // Step 2: Create a Bitmap from the byte array
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        // Step 3: Convert Bitmap to ImageBitmap
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if there's an error
    }
}