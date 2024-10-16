package com.example.dmoneyekyc.Screen.NIDScanning.presentation

sealed class NidScanUiEvent {
    object OcrEventFailed:NidScanUiEvent()
    object OcrEventSuccess:NidScanUiEvent()
    object NidPostEventFailed:NidScanUiEvent()
    object NidPostEventSuccess:NidScanUiEvent()
    data class NidBackPostEventSuccess(val nid:String?,val dob:String?):NidScanUiEvent()
}