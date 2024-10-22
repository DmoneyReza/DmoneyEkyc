package com.example.dmoney.feature_ekyc.SelfieVerification.presentation

import android.location.Location
import com.google.mlkit.vision.common.InputImage

sealed class SelfieInputEvent {
    data class EnterSelfie(val value: InputImage):SelfieInputEvent()
    data class SubmitSelfie(val location: Location?):SelfieInputEvent()

}