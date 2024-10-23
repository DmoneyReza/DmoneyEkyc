package com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase

import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.util.Objects

class GetEcDataUseCase(
    val repository: SelfieRepository
) {
    suspend fun invoke():Flow<Resource<Objects>>{
        return repository.getEcData()
    }
}