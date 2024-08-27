package com.example.dmoney.feature.SelfieVerification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoney.navigation.route.GraphRoute
import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    localStorage: LocalStorageService,
    private val connectivityObserver: ConnectivityObserver
): ViewModel()  {
    val localStorage = localStorage
}