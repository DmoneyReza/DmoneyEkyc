package com.example.dmoneyekyc.Screen.NIDScanning.data


import com.example.dmoneyekyc.Screen.NIDScanning.domain.model.OCRespondsModel
import com.example.dmoneyekyc.Screen.NIDScanning.domain.repository.OcrRepository
import com.example.dmoneyekyc.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import java.util.Objects
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OcrRepositoryImp @Inject constructor(
    val apiService: NidOCRApiService
) : OcrRepository {

    override suspend fun getNidInfo(requestBody: MultipartBody): Flow<Resource<OCRespondsModel>> =
        flow {
            emit(Resource.Loading())


            try {

                // Capture the start time
                val startTime = System.nanoTime()
                val data  =  apiService.getNidInfo(requestBody)
// Capture the end time
                val endTime = System.nanoTime()

                // Calculate the duration in seconds
                val durationInSeconds = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime)

                // Log or show the time (this could be stored and shown in UI)
                println("API call duration: $durationInSeconds seconds")
                emit(
                    Resource.Success(
                        data =data,
                        time = durationInSeconds.toString()
                    )
                )

            }catch (ex:HttpException){
                emit(Resource.Error(
                    message = "${ex.message}"
                ))

            }catch (ex:IOException){

            }
        }

    override suspend fun postToEc(requestBody: MultipartBody): Flow<Resource<Objects>> =flow{

    }
}