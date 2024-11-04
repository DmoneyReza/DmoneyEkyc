package com.example.dmoneyekyc.Screen.SelfieVerification.utli
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun base64ToImageBitmap(base64String: String): Bitmap? {
    return try {
        // Decode the Base64 string to a byte array
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

        // Create a Bitmap from the byte array
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if there's an error
    }
}