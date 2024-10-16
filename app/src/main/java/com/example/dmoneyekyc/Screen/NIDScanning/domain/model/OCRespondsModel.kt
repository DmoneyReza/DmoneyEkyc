package com.example.dmoneyekyc.Screen.NIDScanning.domain.model

data class OCRespondsModel (
   val  successMessage:String?,
   val  errorMessage:String?,
    val data:OCRData
)

data class OCRData(
    val nidName:String?,
    val nidNumber:String?,
    val nidDob:String?,
    val extractedString:String?,
)
