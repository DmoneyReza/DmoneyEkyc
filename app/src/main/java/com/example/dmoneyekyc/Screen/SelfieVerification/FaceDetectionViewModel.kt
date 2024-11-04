package com.example.dmoney.feature.SelfieVerification

import android.content.Context
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
import com.example.dmoneyekyc.Screen.SelfieVerification.presentation.EcResponseState
import com.example.dmoneyekyc.Screen.SelfieVerification.presentation.LivelinessResponseState
import com.example.dmoneyekyc.Screen.SelfieVerification.utli.base64ToImageBitmap
import com.example.dmoneyekyc.util.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import saveBitmapToFile
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

    private val _ecResposneState = mutableStateOf(EcResponseState())
    val ecResponseState = _ecResposneState

    private val _eventFlow = MutableSharedFlow<SelfieUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    fun getEcData(location: Location?, body: RequestBody, context: Context){
            viewModelScope.launch {
                getEcDataUseCase.invoke(localStorage.getString("nid").toString(),localStorage.getString("dob").toString()).onEach {resource ->
                    when(resource){
                        is Resource.Error ->{
                            _ecResposneState.value = ecResponseState.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _ecResposneState.value = ecResponseState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _ecResposneState.value = ecResponseState.value.copy(
                                isLoading = false,
                                response = resource.data!!
                            )
                            val inputStream = context.contentResolver.openInputStream(saveBitmapToFile(context, base64ToImageBitmap(resource.data.scrappedData!!.imageByte)!!)!!)
                            val ecImage = inputStream?.readBytes()?.toRequestBody("image/jpeg".toMediaTypeOrNull())
                            val ecImgaeGson  = Gson().toJson(ecImage)
                            val ecData  = Gson().toJson(resource.data!!)
                            localStorage.putString("ecImage",ecImgaeGson)
                            localStorage.putString("ecData",ecData)



                            postLiveliness(location,body,ecImage)
                        }
                    }

                }.launchIn(this)
            }


    }


    fun postLiveliness(location: Location?, livelinessImage: RequestBody, ecImage: RequestBody?){
        viewModelScope.launch {

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nidImage", "output_image.jpg", ecImage!!)
                .addFormDataPart("normalImage", "output_image.jpg", livelinessImage)
                .build()
            livelinessUseCase.invoke(requestBody).onEach {resource ->
                when(resource){
                    is Resource.Error -> {
                        _livelinessResponseStae.value = livelinessResponseState.value.copy(
                            isLoading = false
                        )
                    }
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