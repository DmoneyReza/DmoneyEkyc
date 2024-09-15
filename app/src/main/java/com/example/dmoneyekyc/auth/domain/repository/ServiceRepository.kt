package com.example.dmoney.auth.domain.repository


import com.example.dmoney.auth.domain.model.Model
import com.example.dmoneyekyc.auth.data.remote.dto.NidFrontModelDto
import com.example.dmoneyekyc.util.Resource

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ServiceRepository {

//    here service methods for api request
suspend fun getAccessToken(): Flow<Resource<Model>>

suspend fun getNidInfo(requestBody:MultipartBody): Flow<Resource<NidFrontModelDto>>
}