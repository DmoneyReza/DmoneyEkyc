package com.example.dmoneyekyc.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.example.dmoneyekyc.R

@Composable
fun ErrorDialog(
    messageBody:String,
    onClick:()->Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000).copy(alpha = .5f))
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { /* handle tap event */ }
            )
        }) {
        Column(
            modifier= Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.alert),
                contentDescription = "Start Icon",
                tint = Color.Unspecified,
                modifier = Modifier.padding(top=57.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Alert", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(15.dp))
          Text(text = messageBody, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium, color =Color(0xFF666666))

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                DButton(
                    IsEnable = true,
                    onClick = {
                        onClick()
                    },
                    text = "Ok"
                )
            }

        }
    }
}