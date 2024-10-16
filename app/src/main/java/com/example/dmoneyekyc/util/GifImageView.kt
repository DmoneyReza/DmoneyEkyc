package com.example.dmoneyekyc.util
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder

import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size



@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    gifDrawable:Int
) {
    var context = LocalContext.current


    val gifEnabledLoader = ImageLoader.Builder(context)
        .components {
            if ( SDK_INT >= 28 ) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    Image(

//        colorFilter = ColorFilter.tint(Color(), blendMode = BlendMode.Darken),
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = gifDrawable).build(), imageLoader = gifEnabledLoader
        ),
        contentDescription = null,
        modifier = modifier,
    )
}



