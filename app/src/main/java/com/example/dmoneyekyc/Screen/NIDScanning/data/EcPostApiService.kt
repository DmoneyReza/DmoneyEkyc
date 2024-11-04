package com.example.dmoneyekyc.Screen.NIDScanning.data
import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.EcPostResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.EcResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EcPostApiService {

    @POST("NID/NIDRequest")
    suspend fun  postDataToEc(@Body body: MultipartBody): EcPostResponseModel


}