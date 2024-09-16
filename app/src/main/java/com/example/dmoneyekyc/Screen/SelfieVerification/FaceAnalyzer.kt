package com.example.dmoney.feature.SelfieVerification


import android.content.Context
import android.media.Image
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.dmoneyekyc.Screen.SelfieVerification.utli.mediaImageToBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import saveBitmapToFile
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceAnalyzer(
    private val onDetectedTextUpdated: (String) -> Unit,
    val onDetectFace: (Face, Image) -> Unit,
    val context: Context,
) : ImageAnalysis.Analyzer {

    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_00L
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    //    private val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val faceRecognition: com.google.mlkit.vision.face.FaceDetector =
        FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()
        )

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scope.launch {
            val mediaImage: Image = imageProxy.image ?: run { imageProxy.close(); return@launch }
            val inputImage: InputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            suspendCoroutine { continuation ->

//                var bitmap = mediaImageToBitmap(mediaImage)
//                var uri = saveBitmapToFile(context, bitmap!!)
//                Log.i("mediaImageToBitmap", "uri: " + uri)
                val copyMediaImage = mediaImage
                faceRecognition.process(inputImage)
                    .addOnSuccessListener { faces ->
                        for (face in faces) {
                            onDetectFace(
                                face,
                                copyMediaImage
                            )
                        }

                    }
                    .addOnCompleteListener {
                        continuation.resume(Unit)
                    }

            }

            delay(THROTTLE_TIMEOUT_MS)
        }.invokeOnCompletion { exception ->
            exception?.printStackTrace()
            imageProxy.close()
        }
    }
}