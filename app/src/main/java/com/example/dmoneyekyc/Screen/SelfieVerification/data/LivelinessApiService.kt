package com.example.dmoneyekyc.Screen.SelfieVerification.data
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.EcResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LivelinessApiService {

    @POST("/api/v1/compare_images")
    suspend fun postLiveliness(@Body  body: MultipartBody): LivelinessResponseModel

}