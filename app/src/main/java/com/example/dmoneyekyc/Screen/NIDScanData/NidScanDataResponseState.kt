package com.example.dmoney.feature_ekyc.NIDScanData.presentation

import com.example.dmoney.feature_ekyc.NIDScanData.domain.NidScanDataResponseModel
import com.example.dmoney.feature_ekyc.NIDVerification.NIDScanning.domain.model.NidResponseModel
import com.example.dmoney.feature_ekyc.NIDVerification.NIDScanning.domain.model.OCRData
import com.example.dmoney.feature_ekyc.NIDVerification.NIDScanning.domain.model.OCRespondsModel

data class NidScanDataResponseState(
    val response: NidScanDataResponseModel = NidScanDataResponseModel(referenceId = ""),
    val isLoading:Boolean = false,
    val fieldError:String="",
    val responseMessage:String = "",
)