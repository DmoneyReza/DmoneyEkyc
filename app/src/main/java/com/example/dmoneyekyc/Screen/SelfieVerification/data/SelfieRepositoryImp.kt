package com.example.dmoneyekyc.Screen.SelfieVerification.data


import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.util.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import java.io.IOException
import java.util.Objects


class SelfieRepositoryImp(
    val livelinessApiService: LivelinessApiService
): SelfieRepository {

    override suspend fun postLiveliness(requestBody: MultipartBody): Flow<Resource<LivelinessResponseModel>> = flow {

    }

    override suspend fun getEcData(): Flow<Resource<Objects>> = flow {
    emit(Resource.Success(
        data = null
    ))

    }
}