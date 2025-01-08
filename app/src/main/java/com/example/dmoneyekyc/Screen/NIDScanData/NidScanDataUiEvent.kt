package com.example.dmoney.feature_ekyc.NIDScanData.presentation

import com.example.dmoneyekyc.Screen.NIDScanning.presentation.NidScanUiEvent

sealed class NidScanDataUiEvent() {
    object EventFailed:NidScanDataUiEvent()
    object EventSuccess:NidScanDataUiEvent()


}