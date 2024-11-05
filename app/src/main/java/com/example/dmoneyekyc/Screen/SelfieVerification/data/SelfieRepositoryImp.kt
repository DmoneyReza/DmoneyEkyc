package com.example.dmoneyekyc.Screen.SelfieVerification.data


import com.example.dmoneyekyc.Screen.SelfieVerification.domain.EcResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException


class SelfieRepositoryImp(
    val livelinessApiService: LivelinessApiService,
   val  ecApiService: EcApiService
): SelfieRepository {

    override suspend fun postLiveliness(requestBody: MultipartBody): Flow<Resource<LivelinessResponseModel>> = flow {
        emit(Resource.Loading())
        try {
            val data  = livelinessApiService.postLiveliness(requestBody)

            emit(
                Resource.Success(
                    data =data,
                )
            )

        }catch (ex: HttpException){
            emit(Resource.Error(
                message = ex.message()
            ))

        }catch (ex:IOException){
            emit(
                Resource.Error(
                    message = ex.message!!
                )
            )

        }

    }

    override suspend fun getEcData(nid: String, dob: String): Flow<Resource<EcResponseModel>> = flow {

        emit(Resource.Loading())
        try {
            val data  = ecApiService.getEcData(nid,dob)

            emit(
                Resource.Success(
                    data =data,
                )
            )

        }catch (ex: HttpException){
            emit(Resource.Error(
                message = ex.message()
            ))

        }catch (ex:IOException){
            emit(
                Resource.Error(
                    message = ex.message!!
                )
            )

        }

    }
}