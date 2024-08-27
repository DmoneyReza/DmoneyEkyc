package com.example.dmoneyekyc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dmoneyekyc.R

val inter = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.inter_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.inter_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.inter_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.inter_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.inter_regular, FontWeight(400), FontStyle.Normal),

    )

// Set of Material typography styles to start with
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(700),
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFFA2A2A2)
    ),
    bodySmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(500),
        fontSize = 13.sp,
        lineHeight = 15.73.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF222222)
    ),
    titleMedium = TextStyle(
        fontFamily = inter,
        fontWeight =FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(500),
        fontSize = 11.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(700),
        fontSize = 20.sp,
        lineHeight = 24.2.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF444444)
    ),

    labelMedium =  TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 16.94.sp,
        letterSpacing = 0.5.sp
    ),

    labelSmall =  TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight(500),
        fontSize = 10.sp,
        lineHeight = 12.1.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF666666)
    )

)