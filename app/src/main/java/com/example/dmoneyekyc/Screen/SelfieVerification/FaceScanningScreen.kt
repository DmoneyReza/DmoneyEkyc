package com.example.dmoney.feature.SelfieVerification

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.location.Location
import android.media.Image
import android.util.Base64
import android.util.Log

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.feature_ekyc.SelfieVerification.presentation.SelfieUiEvent
import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoneyekyc.R
import com.example.dmoneyekyc.Screen.SelfieVerification.utli.correctBitmapOrientation
import com.example.dmoneyekyc.Screen.SelfieVerification.utli.mediaImageToBitmap
import com.example.dmoneyekyc.util.saveBitmapToStorage
import com.example.imagetotextextractor.utlis.working.correctImageOrientation
import com.example.imagetotextextractor.utlis.working.resizeBitmapToFitMaxSize
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import saveBitmapToFile


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
fun FaceScanningScreen(
    viewModel: FaceDetectionViewModel = hiltViewModel(),
    navController: NavController
) {
    val sharedViewModel: ServiceViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.selfie_loader))
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA // Select the front camera
        }
    }

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val GifSize = remember {
        mutableStateOf(0f)
    }
    val headMovement = remember { mutableStateOf("No face detected") }
    val selectedImage = remember { mutableStateOf<Image?>(null) }
    val headMovementThreshold = 10.0f // Threshold for head movement detection

    val HeadMoveMentList = remember {
        mutableListOf<String>()
    }

    var direction = remember {

        mutableStateOf("Front")
    }
    if (headMovement.value != null) {
        if (!HeadMoveMentList.contains(headMovement.value)) {
            if (direction.value == headMovement.value) {

                HeadMoveMentList.add(headMovement.value)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { value: SelfieUiEvent ->
            when(value){
                SelfieUiEvent.eventLivelinessFailed -> {}
                SelfieUiEvent.eventLivelinessSuccess ->{
                    navController.navigate(AuthRoute.Final.route){
                        popUpTo(AuthRoute.Home.route){
                            inclusive = false
                        }
                    }
                }
                SelfieUiEvent.eventSelfieFailed ->{}
                SelfieUiEvent.eventSelfieSuccess -> {
                    navController.navigate(AuthRoute.Final.route){
                        popUpTo(AuthRoute.Home.route){
                            inclusive = false
                        }
                    }
                }
            }
        }

    }



    @SuppressLint("SuspiciousIndentation")
    fun onTextUpdated(face: Face, image: Image) {


        Log.d("headEulerAngleY", "onTextUpdated: " + face.headEulerAngleY)
        headMovement.value = when {
            face.headEulerAngleY > headMovementThreshold -> "Right"
            face.headEulerAngleY < -headMovementThreshold -> "Left"
            face.headEulerAngleX > headMovementThreshold -> "Up"
            face.headEulerAngleX < -headMovementThreshold -> "Down"
//            face.headEulerAngleY < 1.0f && face.headEulerAngleY > -1.0f -> "Front"

            else -> "None"
        }

        if (face.leftEyeOpenProbability!! > 0.6f && face.leftEyeOpenProbability!! > 0.6f
            &&  face.headEulerAngleY < 1.0f && face.headEulerAngleY> -1.0f
            ) {

            Log.d("leftEyeOpenProbability", "FaceScanningScreen: " + "Eye Open")
            if (selectedImage.value == null) {

                selectedImage.value = image
                var bitmap = mediaImageToBitmap(image)

                var uri = saveBitmapToFile(context, bitmap!!)

//                changing orientation
             var rotatedBitmap =    correctBitmapOrientation(context,uri!!, bitmap)


                saveBitmapToStorage(context,rotatedBitmap!!,"selfie")

                sharedViewModel.selfiBitmap.value = rotatedBitmap
                sharedViewModel.eyeOpenFaceImageUri.value = uri



                Log.i("mediaImageToBitmap", "uri: " + uri)
                Log.d("leftEyeOpenProbability", "FaceScanningScreen: " + "${selectedImage.value}")
                headMovement.value = "Front"
            }
        }
//        if(face.leftEyeOpenProbability!! < 0.1f && face.leftEyeOpenProbability!! < 0.1f){
//
//            Log.d("leftEyeOpenProbability", "FaceScanningScreen: "+ "Eye Close" )
//        }





    }

    val progressColor = Color(0xFFF6671F)


    val infiniteTransition = rememberInfiniteTransition()
    val linePosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    fun inputImageToBitmap(inputImage: InputImage): Bitmap? {
        return inputImage.bitmapInternal  // Extract Bitmap if InputImage was created from a Bitmap or MediaImage
    }

    LaunchedEffect(direction.value == "done") {

        if (direction.value == "done") {
            delay(2000) // 2 seconds delay
//            var bitmap = mediaImageToBitmap(selectedImage.value!!)
            val inputStream = context.contentResolver.openInputStream(saveBitmapToFile(context,   resizeBitmapToFitMaxSize(sharedViewModel.selfiBitmap.value!!))!!)
            val fileRequestBody = inputStream?.readBytes()?.toRequestBody("image/jpeg".toMediaTypeOrNull())
            viewModel.getEcData(null,fileRequestBody!! ,context)

//            viewModel.deviceIdManager.getLastKnownLocation { location: Location? ->
//
//            }

//            navController.navigate(AuthRoute.Final.route){
//                popUpTo(AuthRoute.Home.route){
//                    inclusive = false
//                }
//            }

        }

    }


    var sweepAngles = remember {
        mutableStateOf(72f)
    }

//    if (HeadMoveMentList.contains("Front")){
//        sweepAngles.value += 72f
//        direction.value = "Left"
//    }
//    if(HeadMoveMentList.contains("Left")){
//        sweepAngles.value += 72f
//        direction.value = "Right"
//    }
//    if(HeadMoveMentList.contains("Right")){
//        sweepAngles.value += 72f
//        direction.value = "Up"
//    }
//    if(HeadMoveMentList.contains("Up")){
//        sweepAngles.value += 72f
//        direction.value = "Right"
//    }
//    if(HeadMoveMentList.contains("Down")){
//        sweepAngles.value += 72f
//        direction.value = "Right"
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = androidx.compose.ui.Alignment.BottomCenter
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(R.color.black)
                    implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    startTextRecognition(
                        context = context,
                        cameraController = cameraController,
                        lifecycleOwner = lifecycleOwner,
                        previewView = previewView,
                        onDetectedTextUpdated = ::onTextUpdated,

                        )
                }
            }
        )


        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = minOf(canvasWidth, canvasHeight) / 2.5f
            val centerOffset = Offset(x = size.width / 2, y = size.height / 2)
            GifSize.value = radius
            // Draw the background
            drawContext.canvas.nativeCanvas.apply {
                drawColor(android.graphics.Color.argb(128, 0, 0, 0))
                val paint = Paint().apply {

                    xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                }
                drawCircle(canvasWidth / 2, canvasHeight / 2, radius, paint)
            }



            drawCircle(
                color = Color.Gray,
                radius = radius,
                center = centerOffset,
                style = Stroke(
                    width = 3.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )



            if (HeadMoveMentList.contains("Front")) {
                drawArc(
                    color = progressColor,
                    startAngle = 270f,
                    sweepAngle = 72f,
                    useCenter = false,
                    style = Stroke(width = 9f),
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasWidth / 2 - radius,
                        canvasHeight / 2 - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                direction.value = "Left"
            }

            if (HeadMoveMentList.contains("Left")) {

                drawArc(
                    color = progressColor,
                    startAngle = 342f,
                    sweepAngle = 72f,
                    useCenter = false,
                    style = Stroke(width = 9f),
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasWidth / 2 - radius,
                        canvasHeight / 2 - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                direction.value = "Right"
            }


            //up
            if (HeadMoveMentList.contains("Right")) {
                drawArc(
                    color = progressColor,
                    startAngle = 54f,
                    sweepAngle = 72f,
                    useCenter = false,
                    style = Stroke(width = 9f),
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasWidth / 2 - radius,
                        canvasHeight / 2 - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                direction.value = "Up"
            }

            if (HeadMoveMentList.contains("Up")) {
                drawArc(
                    color = progressColor,
                    startAngle = 126f,
                    sweepAngle = 72f,
                    useCenter = false,
                    style = Stroke(width = 9f),
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasWidth / 2 - radius,
                        canvasHeight / 2 - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                direction.value = "Down"
            }


            if (HeadMoveMentList.contains("Down")) {
                drawArc(
                    color = progressColor,
                    startAngle = 198f,
                    sweepAngle = 72f,
                    useCenter = false,
                    style = Stroke(width = 9f),
                    topLeft = androidx.compose.ui.geometry.Offset(
                        canvasWidth / 2 - radius,
                        canvasHeight / 2 - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                direction.value = "done"
            }


        }
        Text(
            text = "Selfie Capture",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )


        LottieAnimation(modifier = Modifier
            .align(Alignment.Center)
            .size(GifSize.value.dp / 1.3f, GifSize.value.dp / 1.3f)
            .clip(CircleShape), contentScale = ContentScale.FillWidth, composition = composition, iterations = LottieConstants.IterateForever )

        Box(
            modifier = Modifier

                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = (screenHeight * 0.15).dp, start = 40.dp, end = 40.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                if (direction.value == "Front") {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_front),
                        contentDescription = "Start Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )


                    Text(
                        text = "Look ahead",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )


                } else if (direction.value == "Left") {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_right),
                        contentDescription = "Start Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )


                    Text(
                        text = "Look to the right",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )


                } else if (direction.value == "Right") {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_left),
                        contentDescription = "Start Icon",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )


                    Text(
                        text = "Look to the left",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )
                } else if (direction.value == "Up") {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_front),
                        contentDescription = "Start Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )


                    Text(
                        text = "Look to the Up",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )
                } else if (direction.value == "Down") {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_front),
                        contentDescription = "Start Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )


                    Text(
                        text = "Look to the Down",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )


                }

                if (direction.value != "done") {
                    Text(
                        text = "Move your head to slowly to complete the box",

                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(500),
                            fontSize = 14.sp
                        ),
                        color = Color.White
                    )
                }

            }
        }


    }

}

private fun startTextRecognition(
    context: Context,
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onDetectedTextUpdated: (Face, Image) -> Unit,

    ) {

    cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        FaceAnalyzer(onDetectedTextUpdated = {},
            onDetectFace = { face, image ->
                onDetectedTextUpdated(face, image)
            },
            context)
    )

    cameraController.bindToLifecycle(lifecycleOwner)
    previewView.controller = cameraController
}
