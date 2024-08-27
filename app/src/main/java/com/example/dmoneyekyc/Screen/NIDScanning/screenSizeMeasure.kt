package com.example.imagetotextextractor.utlis.working

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun getScreenSizeDp(context: Context): Dp {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
    return screenWidthDp.dp // You can return either screenWidthDp or screenHeightDp based on your needs
}

fun getResizableHeight(percent: Double, context: Context):Dp{
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightDp = percent * (displayMetrics.heightPixels / displayMetrics.density)
    return screenHeightDp.dp
}
fun getResizableWidth(percent:Int, context: Context):Dp{
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenWidthDp =percent * displayMetrics.widthPixels / displayMetrics.density
    return screenWidthDp.dp
}