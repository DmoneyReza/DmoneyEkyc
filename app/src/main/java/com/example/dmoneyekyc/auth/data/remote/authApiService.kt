package com.example.dmoney.auth.data.remote

import com.example.dmoney.auth.data.remote.dto.ModelDto
import retrofit2.http.GET

interface authApiService {

    @GET("api/access_token")
    suspend fun getAccessToken():ModelDto
}