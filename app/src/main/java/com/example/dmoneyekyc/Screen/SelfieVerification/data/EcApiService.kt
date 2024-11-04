package com.example.dmoneyekyc.Screen.SelfieVerification.data
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.EcResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EcApiService {

    @GET("nid/niddata")
    suspend fun getEcData(@Query ("nid") nid:String,@Query ("dob") dob:String) :EcResponseModel
}