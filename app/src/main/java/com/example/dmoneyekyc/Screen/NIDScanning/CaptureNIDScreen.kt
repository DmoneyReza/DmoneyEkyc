package com.example.imagetotextextractor.utlis.working

import android.content.Context
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
    val outputDirectory = getOutputDirectory(context)



    Column(  modifier= Modifier.navigationBarsPadding()) {
        CameraView(onImageCaptured = { uri ->
            // Handle the captured image URI
            Log.d("showMyLog", "CaptureNIDScreen: " + uri.toString())

        },
            onImageAccept = {imageBitmap ->


//                viewModel.NIDFront.value = imageBitmap
                val inputStream = context.contentResolver.openInputStream(saveBitmapToFile(context, imageBitmap.asAndroidBitmap())!!)
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

fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}
