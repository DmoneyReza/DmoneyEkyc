package com.example.dmoneyekyc.Screen.NIDScanning.domain.repository

import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.EcPostResponseModel
import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRespondsModel
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.util.Objects

interface OcrRepository {
    suspend fun getNidInfo(requestBody: MultipartBody): Flow<Resource<OCRespondsModel>>

    suspend fun postToEc(requestBody: MultipartBody):Flow<Resource<EcPostResponseModel>>
}