package com.example.dmoney.auth.di


import com.example.dmoney.auth.data.remote.authApiService
import com.example.dmoney.auth.data.remote.repository.ServiceRepositoryImp
import com.example.dmoney.auth.domain.repository.ServiceRepository
import com.example.dmoney.auth.domain.usecase.GetAccessTokenCase
import com.example.dmoneyekyc.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {



    @Provides
    @Singleton
    fun provideOkHttpInstance():OkHttpClient{
        val logginIncepter = HttpLoggingInterceptor()
        logginIncepter.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client  = OkHttpClient.Builder()
            .connectTimeout(
               120,
                TimeUnit.SECONDS
            )
            .writeTimeout(
               120,
                TimeUnit.SECONDS
            )
            .readTimeout(
               120,
                TimeUnit.SECONDS
            )
            .addInterceptor(logginIncepter)

            .build()
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(client :OkHttpClient):Retrofit{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideAuthApiInstance(retrofit:Retrofit):authApiService{
        return retrofit.create(authApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideCharacterRepository(authApi: authApiService ): ServiceRepository {
        return  ServiceRepositoryImp(api = authApi)
    }

    fun provideUseCase(repo:ServiceRepository):GetAccessTokenCase{
        return GetAccessTokenCase(repo)
    }


}