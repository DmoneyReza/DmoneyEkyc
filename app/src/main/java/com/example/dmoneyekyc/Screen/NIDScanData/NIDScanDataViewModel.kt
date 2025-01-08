package com.example.dmoneyekyc.Screen.NIDScanData
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmoney.auth.domain.model.BaseRequestModel
import com.example.dmoney.auth.util.DeviceIdManager
import com.example.dmoney.auth.util.Keys
import com.example.dmoney.auth.util.Network


import com.example.dmoney.feature_ekyc.NIDScanData.presentation.DobState
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidScanDataResponseState
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidScanDataUiEvent
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidScanInputEvent
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidState

import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoney.navigation.route.GraphRoute
import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService
import com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase.PostToEcUseCase
import com.example.dmoneyekyc.util.ValidationResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NIDScanDataViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    localStorage: LocalStorageService,
//    private val connectivityObserver: ConnectivityObserver,
//    val deviceIdManager: DeviceIdManager,
    val validationNidScanDataUseCase: ValidationNidScanDataUseCase,
    val postToEcUseCase: PostToEcUseCase


): ViewModel()  {
    val localStorage = localStorage


    private var _nidResponseState = mutableStateOf(NidScanDataResponseState())
    val nidResponseState = _nidResponseState

    private var _nidState = mutableStateOf(NidState())
    val nidState = _nidState


    private var _dobState = mutableStateOf(DobState())
    val dobState = _dobState


    private val _eventFlow = MutableSharedFlow<NidScanDataUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    val nid = mutableStateOf("")
    val dob = mutableStateOf("")

    init {
        localStorage.putString(GraphRoute.AuthGraph,AuthRoute.EkycSuccessScreen.route)
        savedStateHandle.get<String>("nid")?.let {
                nid.value = it
                _nidState.value = nidState.value.copy(
                    nid = it
                )
        }
        savedStateHandle.get<String>("dob")?.let {
            dob.value = it
            _dobState.value = dobState.value.copy(
                dob = it
            )
        }
    }

//    fun postNidData(location: Location?){
//        viewModelScope.launch {
//            postNidScanDataUseCase.invoke(
//                BaseRequestModel(
//                    data = NidScanDataRequestModel(
//                        isModify = nid.value != nidState.value.nid,
//                        referenceId = localStorage.getString(Keys.referenceId)?:"Unknown",
//                        nidNum = nidState.value.nid,
//                        dob = dobState.value.dob
//                    ),
//                    systemDefault = deviceIdManager.getSystemDefault(
//                        longitude = location?.longitude,
//                        latitude = location?.latitude
//                    )
//                )
//            ).onEach {resource ->
//                when(resource){
//                    is Resource.Error ->{
//                        when (resource.data?.responseCode){
//                            Network.STATUS_400->{
//
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false,
//                                    fieldError = resource.data.responseMessage
//                                )
//                            }
//                            else ->{
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false,
//
//                                )
//                            }
//                        }
//
//
//                    }
//                    is Resource.Loading -> {
//                        _nidResponseState.value = nidResponseState.value.copy(
//                            isLoading = true
//                        )
//                    }
//                    is Resource.Success -> {
//                        when (resource.data?.responseCode){
//                            Network.STATUS_200->{
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false
//                                )
//                                _eventFlow.emit(NidScanDataUiEvent.EventSuccess)
//                            }
//                            Network.STATUS_100->{
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false,
//                                    responseMessage = resource.data.responseMessage
//                                )
//
//                                _eventFlow.emit(NidScanDataUiEvent.EventFailed)
//                            }
//
//                        }
//
//                    }
//                }
//
//            }.launchIn(this)
//        }
//
//    }

    fun eventListener(event: NidScanInputEvent){
        when(event){
            is NidScanInputEvent.EnteredDate -> {
                _dobState.value = dobState.value.copy(
                    dob = event.value
                )
            }
            is NidScanInputEvent.EnteredNid -> {

                _nidState.value = nidState.value.copy(
                    nid = event.value
                )
            }
            is NidScanInputEvent.SubmitEvent ->{

                val nidResult = validationNidScanDataUseCase.executeNid(nidState.value.nid)
                val dobResult = validationNidScanDataUseCase.executeDate(dobState.value.dob)
                when(nidResult){
                    is ValidationResult.Error -> {
                        _nidState.value = nidState.value.copy(
                           nidError = nidResult.message
                        )
                    }
                    ValidationResult.Success -> {
                        _nidState.value = nidState.value.copy(
                            nidError = ""
                        )
                        when(dobResult){
                            is ValidationResult.Error -> {
                                _dobState.value = dobState.value.copy(
                                    dobError = dobResult.message
                                )
                            }
                            ValidationResult.Success -> {
                                _dobState.value = dobState.value.copy(
                                    dobError = ""
                                )
//                                postNidData(event.value)
                            }

                        }


                    }
                }

            }
        }
    }

}