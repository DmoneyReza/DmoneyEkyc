package com.example.imagetotextextractor.utlis.working

import DrawCenteredFrame
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoneyekyc.R
import com.example.dmoneyekyc.Screen.NIDScanning.presentation.NidProcessViewModel
import com.example.dmoneyekyc.Screen.NIDScanning.presentation.NidScanUiEvent
import com.example.dmoneyekyc.util.GifImage
import com.example.imagetotextextractor.utlis.OCR.PerformOCR
import kotlinx.coroutines.flow.collectLatest


import java.io.File

@Composable
fun CameraView(
    viewModel: ServiceViewModel = hiltViewModel(),
    navController: NavController,
    onImageCaptured: (Uri) -> Unit,
    onImageAccept:(ImageBitmap)->Unit,
    onOCR:(String)->Unit,
    nidProcessViewModel: NidProcessViewModel
    ) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.nid_loader))

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var capturedImage = remember { mutableStateOf<ImageBitmap?>(null) }

    var label = remember {
        mutableStateOf("NID Front Side Capture")
    }
    var instruction = remember {
        mutableStateOf("Place & hold your NID card front side within the frame and take a photo")
    }
    var isOcrErrorDialogShow by remember { mutableStateOf(false) }
    var isNidPostErrorDialogShow by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        nidProcessViewModel.eventFlow.collectLatest { event ->
            when (event) {
                NidScanUiEvent.NidPostEventFailed -> {
                    isNidPostErrorDialogShow = true
                }
                NidScanUiEvent.NidPostEventSuccess -> {
                    capturedImage.value = null
                    label.value = "NID Back Side Capture"
                    instruction.value =
                        "Place & hold your NID card back side within the frame and take a photo"
                    Toast.makeText(context,nidProcessViewModel.responseTime.value + " seconds",Toast.LENGTH_SHORT).show()
                }

                NidScanUiEvent.OcrEventFailed -> {}
                NidScanUiEvent.OcrEventSuccess -> {

                }

                is NidScanUiEvent.NidBackPostEventSuccess -> {
                    navController.navigate(AuthRoute.NIDScanData.route+"?nid=${event.nid?:""}"+"?dob=${event.dob ?:""}") {
                        popUpTo(AuthRoute.SignUp.route) {
                            inclusive = false
                        }
                    }
                }


            }

        }

    }



    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
   val width = getScreenSizeDp(LocalContext.current) - 25.dp
   val height = getResizableHeight(0.3, LocalContext.current)

    Box(modifier = Modifier.fillMaxSize()
        ) {


        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                imageCapture.value = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture.value
                    )
                } catch (exc: Exception) {
                    Log.e("CameraView", "Use case binding failed", exc)
                }
            }, executor)

            previewView
        })


        DrawCenteredFrame(
            width = getScreenSizeDp(LocalContext.current) - 25.dp,
            height = getResizableHeight(0.3, LocalContext.current)
        )

        if(capturedImage.value ==null){
            LottieAnimation(modifier = Modifier
                .align(Alignment.Center)
                .size(width= getScreenSizeDp(LocalContext.current) - 25.dp,height= getResizableHeight(0.3, LocalContext.current))
                .clip(RoundedCornerShape(16.dp)), contentScale = ContentScale.FillBounds, composition = composition, iterations = LottieConstants.IterateForever )
        }


        capturedImage.let { images ->
            images.value?.let {
                drawAndCropRectangleOnImageBitmap(images.value!!)?.let { image ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()

                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

//                            PerformOCR( image)


                        Image(

                            bitmap = image,
                            contentScale =ContentScale.FillBounds ,
                            contentDescription = null,
                            modifier = Modifier
                                .width(width = width)
                                .height(height)
                                .clip(RoundedCornerShape(16.dp))

                        )
                    }

                }
            }
        }

            Text(
                text = label.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp)
                , textAlign = TextAlign.Center,
                style =MaterialTheme.typography.titleMedium,
                color = Color.White
            )


     Box(modifier =  Modifier

         .fillMaxWidth()
         .align(Alignment.BottomCenter)
         .padding(bottom = (screenHeight * 0.25).dp, start = 40.dp, end = 40.dp)){

         Box(modifier = Modifier

             .align(Alignment.Center)
             .clip(shape = RoundedCornerShape(6.dp))
             .background(Color(0xFF000000).copy(alpha = .33f))
             .padding(top = 10.dp, bottom = 10.dp, start = 14.dp, end = 14.dp)){

             if(capturedImage.value !=null){
                 Text(
                     text = "Please ensure the NID Photo is clear enough",

                     textAlign = TextAlign.Center,
                     style =TextStyle(
                         fontFamily = FontFamily(Font(R.font.inter)),
                         fontWeight = FontWeight(500),
                         fontSize = 14.sp
                     ),
                     color = Color.White
                 )
             }else{
                 Text(
                     text = instruction.value,

                     textAlign = TextAlign.Center,
                     style =TextStyle(
                         fontFamily = FontFamily(Font(R.font.inter)),
                         fontWeight = FontWeight(500),
                         fontSize = 14.sp
                     ),
                     color = Color.White
                 )
             }

         }
     }

        if (nidProcessViewModel.ocrResponseState.value.isLoading ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GifImage(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp),
                    gifDrawable = R.drawable.dmoney_loader
                )
            }

        }





        Row (modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp)){





            if( capturedImage.value!=null){
                CircleWithIcon(
                    onClick = {
                        capturedImage.value = null
                    },
                    icon = R.drawable.flip_camera_ios,
                    color = Color(0xFF859696)
                )

                CircleWithIcon(
                    onClick = {
//                        capturedImage.value?.let { onImageAccept(it) }
                        if(label.value.contains("Front")){
                            nidProcessViewModel.NIDFront.value = capturedImage.value
                            onImageAccept(capturedImage.value!!)

//                            viewModel.uploadNidFront(imageUri.value.toString())
                        }else if(label.value.contains("Back")){
                            viewModel.NIDBack.value = capturedImage.value
                            nidProcessViewModel.postNidToEc(nidProcessViewModel.localStorage.getString("nid").toString(),nidProcessViewModel.localStorage.getString("dob").toString())

//                            navController.navigate(AuthRoute.Final.route){
//                                popUpTo(AuthRoute.Home.route){
//                                    inclusive = false
//                                }
//                            }

                        }
//                        capturedImage.value = null
//                        label.value = "NID Back Side Capture"
//                        instruction.value = "Place & hold your NID card back side within the frame and take a photo"


                    },
                    icon = R.drawable.ic_check
                )
            }else{
                CaptureButton(
                    modifier = Modifier

                        .padding(bottom = 16.dp)
                ) {

                    val photoFile = File(
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "photo_${System.currentTimeMillis()}.jpg"
                    )

                    Log.d("imageCapture", "CameraView: " + imageCapture.value)
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                    imageCapture.value?.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exc: ImageCaptureException) {
                                Log.e("CaptureImage", "Photo capture failed: ${exc.message}", exc)
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                onImageCaptured(savedUri)
                                imageUri.value = savedUri
                                // Handle the saved image URI

                                val imageBitmap = loadImageBitmapFromUri(context, savedUri)

                                //changes uri to bitmap
                                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(savedUri))
                                // Correct the orientation of the bitmap
                                val rotatedBitmap = correctImageOrientation(savedUri, bitmap)


                                capturedImage.value = rotatedBitmap.asImageBitmap()



                            }
                        }
                    )
                }
            }

        }



    }
}


@Composable
fun CaptureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Canvas(
        modifier = modifier
            .size(64.dp)
            .clickable { onClick() }
    ) {
        drawCircle(
            color = Color.Gray, // Border color
            center = center,
            radius = size.minDimension / 2f * 1.15f, // Radius 15% larger
            style = Stroke(width = 4f) // Adjust the width of the border as needed
        )
        drawCircle(
            color = Color.White,
            center = center,
            radius = size.minDimension / 2f
        )
    }
}

@Composable
fun CircleWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon:Int = R.drawable.flip_camera_ios,
    color:Color=MaterialTheme.colorScheme.primary
) {
    Box(modifier = Modifier  .size(77.dp)){

        Canvas(
            modifier = modifier
                .size(77.dp)
                .clickable { onClick() }
        ) {
            drawCircle(
                color = color,
                center = center,
                radius = size.minDimension / 2f
            )
        }

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Start Icon",
            tint = Color(0xFFFFFFFF),
            modifier = Modifier
                .align(Alignment.Center)
                .size(36.dp), // Adjust size of the icon as needed// Center the icon within the parent

        )
    }



    // Icon centered within the circle

}

@Composable
fun DisplayImageFromDrawable(drawableId: Int) {
    val imageBitmap = drawableToImageBitmap(drawableId)
    Image(bitmap = imageBitmap, contentDescription = null)
}

@Composable
fun drawableToImageBitmap(drawableId: Int): ImageBitmap {
    val context = LocalContext.current
    val drawable = ResourcesCompat.getDrawable(context.resources, drawableId, context.theme)

    val bitmap = (drawable as BitmapDrawable).bitmap
    return bitmap.asImageBitmap()
}