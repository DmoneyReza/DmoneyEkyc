package com.example.dmoney.feature_ekyc.NIDScanData.presentation

import android.location.Location

sealed class NidScanInputEvent {
    data class EnteredNid(val value:String):NidScanInputEvent()
    data class EnteredDate(val value:String):NidScanInputEvent()
    data class SubmitEvent(val value:Location?):NidScanInputEvent()

}