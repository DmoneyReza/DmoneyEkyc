package com.example.dmoney.feature.SelfieVerification

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmoney.auth.util.DeviceIdManager
import com.example.dmoney.feature_ekyc.SelfieVerification.presentation.SelfieUiEvent
import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.GetEcDataUseCase
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.PostLivelinessUseCase
import com.example.dmoneyekyc.Screen.SelfieVerification.presentation.LivelinessResponseState
import com.example.dmoneyekyc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    localStorage: LocalStorageService,
    private val connectivityObserver: ConnectivityObserver,
    val deviceIdManager: DeviceIdManager,

    val livelinessUseCase: PostLivelinessUseCase,
    val getEcDataUseCase: GetEcDataUseCase
): ViewModel()  {
    val localStorage = localStorage

    private val _livelinessResponseStae = mutableStateOf(LivelinessResponseState())
    val livelinessResponseState = _livelinessResponseStae

    private val _eventFlow = MutableSharedFlow<SelfieUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    fun getEcData(location: Location?, body: RequestBody){
            viewModelScope.launch {
                getEcDataUseCase.invoke().onEach {resource ->
                    when(resource){
                        is Resource.Error ->{}
                        is Resource.Loading -> {}
                        is Resource.Success -> {

                            postLiveliness(location,body)
                        }
                    }

                }.launchIn(this)
            }


    }


    fun postLiveliness(location: Location?, body: RequestBody){
        viewModelScope.launch {

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "output_image.jpg", body)
                .addFormDataPart("file", "output_image.jpg", body)
                .build()
            livelinessUseCase.invoke(requestBody).onEach {resource ->
                when(resource){
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        _livelinessResponseStae.value = livelinessResponseState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _livelinessResponseStae.value = livelinessResponseState.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(SelfieUiEvent.eventLivelinessSuccess)
                    }
                }
            }.launchIn(this)
        }
    }
}