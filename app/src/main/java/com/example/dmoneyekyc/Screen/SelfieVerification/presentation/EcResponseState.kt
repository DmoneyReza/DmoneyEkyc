package com.example.dmoneyekyc.Screen.SelfieVerification.presentation

import com.example.dmoneyekyc.Screen.SelfieVerification.domain.EcResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel

data class EcResponseState (
    val response: EcResponseModel = EcResponseModel("","",null),
    val isLoading:Boolean = false,
    val fieldError:String="",
    val responseMessage:String = "",
)