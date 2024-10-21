package com.example.dmoneyekyc.Screen.SelfieVerification.domain

import com.example.dmoney.auth.domain.model.BaseRequestModel
import com.example.dmoney.auth.domain.model.BaseRespondModel

import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface SelfieRepository {
//    suspend fun postSelfie(model:BaseRequestModel<SelfieRequestModel>):Flow<Resource<BaseRespondModel<SelfieResponseModel>>>

    suspend fun postLiveliness(requestBody: MultipartBody):Flow<Resource<LivelinessResponseModel>>
}