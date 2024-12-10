package com.example.imagetotextextractor.utlis.working

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoneyekyc.R
import com.example.dmoneyekyc.Screen.NIDScanning.presentation.NidProcessViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import saveBitmapToFile

import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CaptureNIDScreen(
    viewModel: ServiceViewModel = hiltViewModel(),
    navController: NavController,
    nidProcessViewModel: NidProcessViewModel = hiltViewModel()
) {
    val sharedViewModel: ServiceViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }



    Column(  modifier= Modifier.navigationBarsPadding()) {
        CameraView(onImageCaptured = { uri ->
            // Handle the captured image URI
            Log.d("showMyLog", "CaptureNIDScreen: " + uri.toString())

        },
            onImageAccept = {imageBitmap ->
//
//                Log.d("imageBitmap", "width: " + imageBitmap.width )
//                Log.d("imageBitmap", "height: " + imageBitmap.height )
//                viewModel.NIDFront.value = imageBitmap


                val inputStream = context.contentResolver.openInputStream(saveBitmapToFile(context,
                    resizeBitmapToFitMaxSize(imageBitmap.asAndroidBitmap()) )!!)
                val fileRequestBody = inputStream?.readBytes()?.toRequestBody("image/jpeg".toMediaTypeOrNull())
//                sharedViewModel.uploadNidFront(fileRequestBody!!)
                nidProcessViewModel.deviceIdManager.getLastKnownLocation { location ->
                    nidProcessViewModel.getOcrInfo(location,fileRequestBody!!)
                }
            },
            onOCR = {},
            navController = navController,
            nidProcessViewModel = nidProcessViewModel
        )

    }
}




fun getImageDimensions(context: Context, imageUri: Uri): Pair<Int, Int>? {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    inputStream?.use { stream ->
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true // Decode only bounds, not the full bitmap
        }
        BitmapFactory.decodeStream(stream, null, options)

        // Width and height of the image
        val width = options.outWidth
        val height = options.outHeight

        if (width > 0 && height > 0) {
            Log.d("getImageDimensions", "width: " + width )
            Log.d("getImageDimensions", "height: " + height )

            return Pair(width, height)
        }
    }
    return null // Return null if dimensions could not be determined
}



fun resizeBitmapToFitMaxSize(imageBitmap: Bitmap, maxDimension: Int = 1024): Bitmap {
    val width = imageBitmap.width
    val height = imageBitmap.height

    if (width <= maxDimension && height <= maxDimension) {
        // If already within the size limit, return the original bitmap
        return imageBitmap
    }

    // Calculate the new dimensions while maintaining the aspect ratio
    val aspectRatio = width.toFloat() / height.toFloat()
    val newWidth: Int
    val newHeight: Int

    if (width > height) {
        newWidth = maxDimension
        newHeight = (maxDimension / aspectRatio).toInt()
    } else {
        newHeight = maxDimension
        newWidth = (maxDimension * aspectRatio).toInt()
    }

    // Resize the bitmap
    return Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true)
}




