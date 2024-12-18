package com.example.dmoneyekyc.Screen.SelfieVerification.presentation

import com.example.dmoneyekyc.Screen.SelfieVerification.domain.FaceMatchData
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel

data class LivelinessResponseState (
    val response: LivelinessResponseModel = LivelinessResponseModel(successMessage = null, errorMessage = null, data = FaceMatchData()),
    val isLoading:Boolean = false,
    val fieldError:String="",
    val responseMessage:String = "",
)