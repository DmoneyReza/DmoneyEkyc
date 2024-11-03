package com.example.dmoneyekyc.Screen.NIDScanning.data


import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.EcPostResponseModel
import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRespondsModel

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.POST

interface NidOCRApiService {
    @POST("api/textExtraction/uploadNID")
    suspend fun getNidInfo(@Body body: MultipartBody): OCRespondsModel

    @POST("NID/NIDRequest")
    suspend fun  postDataToEc(@Body body: MultipartBody): EcPostResponseModel


}