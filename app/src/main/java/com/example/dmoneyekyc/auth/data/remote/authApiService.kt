package com.example.dmoney.auth.data.remote

import com.example.dmoney.auth.data.remote.dto.ModelDto
import com.example.dmoneyekyc.auth.data.remote.dto.NidFrontModelDto
import retrofit2.http.GET

interface authApiService {

    @GET("api/access_token")
    suspend fun getAccessToken():ModelDto

    @GET("api/textExtraction/uploadNID")
    suspend fun getNidInfo():NidFrontModelDto
}