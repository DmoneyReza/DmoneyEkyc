package com.example.dmoneyekyc.Screen.SelfieVerification.utli

import java.text.SimpleDateFormat
import java.util.Locale

fun changeDateFormat(dateString: String, fromFormat: String, toFormat: String): String? {
    return try {
        val inputFormat = SimpleDateFormat(fromFormat, Locale.getDefault())
        val outputFormat = SimpleDateFormat(toFormat, Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) }
    } catch (e: Exception) {
        null // Return null if there's a parsing error
    }
}