package com.example.dmoney.auth.presentation

import android.Manifest
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmoney.auth.domain.usecase.GetAccessTokenCase
import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService
import com.example.dmoneyekyc.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val authUseCase:GetAccessTokenCase,
    savedStateHandle: SavedStateHandle,
    localStorage: LocalStorageService,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {
//    here keeping the state of our concurrent api calls for authentication methodology.

    val localStorage = localStorage

    private val _accessTokenState = mutableStateOf(authState())
    val accessToken = _accessTokenState

    private val _networkStatus = MutableStateFlow<ConnectivityObserver.Status>(ConnectivityObserver.Status.Unavailable)
    val networkStatus = _networkStatus

    //accessing local storage
    val retrive = localStorage.getString("Key")

    val NIDFront = mutableStateOf<ImageBitmap?>(null)
    val NIDFrontOCR = mutableStateOf("")

    val NIDBack = mutableStateOf<ImageBitmap?>(null)
    val NIDBackOCR = mutableStateOf("")

    val BirthCertificate = mutableStateOf<ImageBitmap?>(null)



    init{
        observeNetworkStatus()
        getAccessToken()
        localStorage.putString("Key","Hello World")//just for test
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                _networkStatus.value = status
            }
        }
    }


    fun getAccessToken(){
            viewModelScope.launch {
                authUseCase.invoke().onEach{resources->
                    when(resources){
                        is Resource.Error -> {
                            _accessTokenState.value = accessToken.value.copy(
                                message = resources.message.toString(),
                                isLoading = false,
                                data = resources.data
                            )
                        }
                        is Resource.Loading -> {
                            _accessTokenState.value = accessToken.value.copy(
                                isLoading = true

                            )
                        }
                        is Resource.Success ->{
                            _accessTokenState.value = accessToken.value.copy(
                                isLoading = true,
                                data = resources.data!!,
                            )
                        }
                    }

                }.launchIn(this)
            }
    }
}

