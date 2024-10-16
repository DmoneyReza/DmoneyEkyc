package com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase

import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRespondsModel
import com.example.dmoneyekyc.Screen.NIDScanning.domain.repository.OcrRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class GetNidOcrUseCase(
  val  repository: OcrRepository
) {

    suspend fun invoke(body: MultipartBody):Flow<Resource<OCRespondsModel>>{
        return repository.getNidInfo(body)
    }
}