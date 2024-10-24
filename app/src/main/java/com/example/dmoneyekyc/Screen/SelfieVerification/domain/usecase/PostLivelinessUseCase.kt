package com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase

import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class PostLivelinessUseCase(
    val repository: SelfieRepository
) {
    suspend fun invoke(body: MultipartBody):Flow<Resource<LivelinessResponseModel>>{
        return repository.postLiveliness(body)
    }
}