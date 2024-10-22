package com.example.dmoney.feature_ekyc.SelfieVerification.presentation

sealed class SelfieUiEvent {
    object eventSelfieFailed: SelfieUiEvent()
    object eventSelfieSuccess: SelfieUiEvent()
    object eventLivelinessFailed: SelfieUiEvent()
    object eventLivelinessSuccess: SelfieUiEvent()


}