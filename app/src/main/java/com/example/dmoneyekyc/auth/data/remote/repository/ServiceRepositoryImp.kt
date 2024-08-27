package com.example.dmoney.auth.data.remote.repository

import coil.network.HttpException
import com.example.dmoney.auth.data.remote.authApiService
import com.example.dmoney.auth.data.remote.dto.ModelDto
import com.example.dmoney.auth.domain.model.Model
import com.example.dmoney.auth.domain.repository.ServiceRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import java.util.Date
import javax.inject.Inject

//add dependency
class ServiceRepositoryImp @Inject constructor(
  val  api:authApiService
) :ServiceRepository {

    //    below services goes here
//    Access Token
//    Session
//    Encryption data match


    val dummyTokenModel = ModelDto(
        access_token = "lrc-bz765X4TREayk5tDaaJJ8YHb492-uHhYfxuQRkquM5QMc-AtGfiutbPXcAL22j9ExfGelW8p0xeJm8LpeD1Zk_UhAeiAL49iZpHVODTtB9l7ofrNQmcvxLQ5PJ2e4of3osk-MhhGysSZvOq72sm_gURiWpnR2QmMrBR5qscpvtOy-WPtYrZ9y5_dqo5glKgq_NTEYOtIfrwjfqRPKG56LrM",
        token_type = "bearer",
        expires_in = 12346,
        created_at = Date()

    )
    override suspend fun getAccessToken(): Flow<Resource<Model>> = flow{

       emit(Resource.Loading())

        try {
            val data =  api.getAccessToken()
            emit(Resource.Success(data.toModel()))
        }catch (ex:HttpException){
            emit(Resource.Error(
                message = "Server Error,so we are providing dummy data",
                data = dummyTokenModel.toModel()
            ))

        }catch (ex:IOException){
            emit(Resource.Error(
                message = "Network Connection Error ,so we are providing dummy data",
                data = dummyTokenModel.toModel()
            ))
        }

    }
}