package com.example.dmoneyekyc.Screen.NIDScanning.presentation

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmoney.auth.domain.model.BaseRequestModel
import com.example.dmoney.auth.util.DeviceIdManager
import com.example.dmoney.auth.util.Keys
import com.example.dmoney.auth.util.Network

import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService

import com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase.GetNidOcrUseCase
import com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase.PostToEcUseCase
import com.example.dmoneyekyc.Screen.SelfieVerification.utli.changeDateFormat
import com.example.dmoneyekyc.util.Resource
import com.google.gson.Gson
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
class NidProcessViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val localStorage: LocalStorageService,
//    val  translationLoader: TranslationLoader,
    private val connectivityObserver: ConnectivityObserver,
    val deviceIdManager: DeviceIdManager,
    val getNidOcrUseCase: GetNidOcrUseCase,
//    val postNidUseCase: PostNidUseCase
    val postToEcUseCase: PostToEcUseCase
):ViewModel() {

    private val _ocrResponseState = mutableStateOf(OcrResponseState())
    val ocrResponseState = _ocrResponseState

//    private val _nidResponseState = mutableStateOf(NidPostResponseState())
//    val nidResponseState = _nidResponseState

    val NIDFront = mutableStateOf<ImageBitmap?>(null)
    val NIDBack = mutableStateOf<ImageBitmap?>(null)

    private val _eventFlow = MutableSharedFlow<NidScanUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

     val responseTime = mutableStateOf("0")




    fun getOcrInfo(location: Location?,requestBody: RequestBody){
        viewModelScope.launch {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "output_image.jpg", requestBody)  // Use the file's name
//                .addFormDataPart("pageMode", "11")
//                .addFormDataPart("ocrEngineMode", "1")
//                .addFormDataPart("lang", "eng")
                .build()
            getNidOcrUseCase.invoke(requestBody).onEach {  resource ->
                when(resource){
                    is Resource.Error -> {}
                    is Resource.Loading ->{
                        _ocrResponseState.value = ocrResponseState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success ->{
                        _ocrResponseState.value = ocrResponseState.value.copy(
                            isLoading = false,
                            response = resource.data!!
                        )
                        responseTime.value = resource.time!!
                        localStorage.putString("ocrResponse",responseTime.toString())

                        Log.d("_ocrResponseState", "getOcrInfo: " + Gson().toJson(ocrResponseState.value.response))

                        val formattedDate = changeDateFormat(ocrResponseState.value.response.data[0].nidDob.toString(), "dd MMM yyyy", "dd/MM/yyyy")
                        localStorage.putString("nid",ocrResponseState.value.response.data[0].nidNumber.toString()?:"")
                        localStorage.putString("dob",formattedDate?:"")
//                        postNidInfo(location,resource.data)
//                        postNidToEc(ocrResponseState.value.response.data.nidNumber.toString(),ocrResponseState.value.response.data.nidDob.toString())
                        _eventFlow.emit(NidScanUiEvent.NidPostEventSuccess)
                    }
                }

            }.launchIn(this)
        }
    }


    fun postNidToEc(
        nid:String,dob:String
    ){
        viewModelScope.launch {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM) // Use the file's name
                .addFormDataPart("NID", nid)
                .addFormDataPart("DOB", dob)
                .build()

            postToEcUseCase.invoke(requestBody).onEach { resource ->
                when(resource){
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        when(resource.data?.status == "OK"){
                            true -> {
                                _eventFlow.emit(NidScanUiEvent.NidBackPostEventSuccess(nid, dob))
                                responseTime.value = resource.time!!
                                localStorage.putString("postToEc",responseTime.toString())
                            }
                            false ->{}
                        }

                    }
                }


            }.launchIn(this)
        }


    }



//    fun postNidInfo(location: Location?,ocrOCRespondsModel: OCRespondsModel){
//        viewModelScope.launch {
//
//            postNidUseCase.invoke(
//                BaseRequestModel(
//                    data = NidRequestModel(
//                        step = "nid-front",
//                        nid_front = "nid-front.png",
//                        nidNum = ocrOCRespondsModel.data.nidNumber?:"",
//                        dob = ocrOCRespondsModel.data.nidDob?:""
//                    ),
//                    systemDefault = deviceIdManager.getSystemDefault(
//                        longitude = location?.longitude,
//                        latitude = location?.latitude
//                    )
//                )
//            ).onEach {resource ->
//                when(resource){
//                    is Resource.Error -> {}
//                    is Resource.Loading -> {
//                        _nidResponseState.value = nidResponseState.value.copy(
//                            isLoading = true
//                        )
//                    }
//                    is Resource.Success ->{
//                        when(resource.data?.responseCode){
//                            Network.STATUS_200->{
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false
//                                )
//                                _eventFlow.emit(NidScanUiEvent.NidPostEventSuccess)
//                            }
//                            Network.STATUS_100->{
//                                _nidResponseState.value = _nidResponseState.value.copy(
//                                    isLoading = false,
//                                    responseMessage = resource.data.responseMessage
//                                )
//                                _eventFlow.emit(NidScanUiEvent.NidPostEventFailed)
//                            }
//                        }
//
//                    }
//                }
//            }.launchIn(this)
//        }
//    }

//    fun postNidBackInfo(location: Location?, image: String){
//        viewModelScope.launch {
//            postNidUseCase.invoke(
//                BaseRequestModel(
//                    data = NidRequestModel(
//                        step = "nid-back",
//                        nid_back = image,
//                        referenceId = localStorage.getString(Keys.referenceId)?:"unknown"
//                    ),
//                    systemDefault = deviceIdManager.getSystemDefault(
//                        longitude = location?.longitude,
//                        latitude = location?.latitude
//                    )
//                )
//            ).onEach {resource ->
//                when(resource){
//                    is Resource.Error ->{}
//                    is Resource.Loading -> {
//                        _nidResponseState.value = nidResponseState.value.copy(
//                            isLoading = true
//                        )
//                    }
//                    is Resource.Success ->{
//                        when(resource.data?.responseCode){
//                            Network.STATUS_200->{
//                                _nidResponseState.value = nidResponseState.value.copy(
//                                    isLoading = false
//                                )
//                                _eventFlow.emit(NidScanUiEvent.NidBackPostEventSuccess(ocrResponseState.value.response.data.nidNumber,ocrResponseState.value.response.data.nidDob))
//                            }
//                            Network.STATUS_100->{
//                                _nidResponseState.value = _nidResponseState.value.copy(
//                                    isLoading = false,
//                                    responseMessage = resource.data.responseMessage
//                                )
//                                _eventFlow.emit(NidScanUiEvent.NidPostEventFailed)
//                            }
//                        }
//
//                    }
//                }
//
//            }.launchIn(this)
//        }
//    }


}