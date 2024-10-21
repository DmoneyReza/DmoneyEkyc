package com.example.dmoneyekyc.Screen.SelfieVerification.data
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LivelinessApiService {

    @POST("api/faceExtraction/matchFace")
    suspend fun postLiveliness(@Body  body: MultipartBody): LivelinessResponseModel
}