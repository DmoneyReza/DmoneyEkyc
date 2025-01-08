package com.example.dmoney.feature_ekyc.NIDScanData.presentation

import com.example.dmoneyekyc.Screen.NIDScanData.NidScanDataResponseModel


data class NidScanDataResponseState(
    val response: NidScanDataResponseModel = NidScanDataResponseModel(referenceId = ""),
    val isLoading:Boolean = false,
    val fieldError:String="",
    val responseMessage:String = "",
)