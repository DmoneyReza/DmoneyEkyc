package com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase

import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.EcPostResponseModel
import com.example.dmoneyekyc.Screen.NIDScanning.domain.repository.OcrRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.util.Objects

class PostToEcUseCase(
  val  repository: OcrRepository
) {

    suspend fun invoke(body: MultipartBody):Flow<Resource<EcPostResponseModel>>{
        return repository.postToEc(body)
    }
}