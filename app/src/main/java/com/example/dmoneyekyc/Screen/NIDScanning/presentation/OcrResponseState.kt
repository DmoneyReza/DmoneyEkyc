package com.example.dmoneyekyc.Screen.NIDScanning.presentation


import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRData
import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRespondsModel

data class OcrResponseState(
    val response: OCRespondsModel = OCRespondsModel(successMessage = null, errorMessage = null, data = OCRData(nidName = null, nidNumber = null, nidDob = null, extractedString = null)),
    val isLoading:Boolean = false,
    val fieldError:String="",
    val responseMessage:String = "",
)