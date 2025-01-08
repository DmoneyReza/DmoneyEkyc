package com.example.dmoneyekyc.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dmoney.navigation.route.AuthRoute

@Composable
fun DButton(
    IsEnable:Boolean = true,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    disableColor:Color = MaterialTheme.colorScheme.secondary,
    contentColor:Color = Color.White,
    onClick:()->Unit,
    text:String = ""
) {

    Button(modifier = Modifier
        .fillMaxWidth()
        .height(52.dp),
        shape = RoundedCornerShape(111.dp),
        onClick = {
            onClick()
        },
        enabled = IsEnable,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = disableColor,
            containerColor = primaryColor,
            contentColor = contentColor,
            disabledContentColor = contentColor
        )

    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()

              ,

            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,

        )

    }
}