package com.example.dmoney.feature_ekyc.NIDScanData.presentation

sealed class NidScanDataUiEvent() {
    object EventFailed:NidScanDataUiEvent()
    object EventSuccess:NidScanDataUiEvent()
}