package com.example.imagetotextextractor.utlis.OCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun PerformOCR(imageBitmap: ImageBitmap):String{
    var recognizedText by remember { mutableStateOf("") }

    LaunchedEffect(imageBitmap) {
        val inputImage = convertImageBitmapToInputImage(imageBitmap)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.Builder().build())


        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                recognizedText = visionText.text
            }
            .addOnFailureListener { e ->
                recognizedText = "Failed to recognize text: ${e.message}"
            }
    }
    // Find the first match of the pattern in the OCR text

// Extract the date of birth if a match is found
//    Text(text =recognizedText , modifier = Modifier.fillMaxWidth(), style = TextStyle(fontSize = 20.sp), color = Color.White)

    return recognizedText



}
