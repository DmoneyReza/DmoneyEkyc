package com.example.dmoneyekyc.Screen.SelfieVerification.di
import com.example.dmoneyekyc.Screen.SelfieVerification.data.LivelinessApiService
import com.example.dmoneyekyc.Screen.SelfieVerification.data.SelfieRepositoryImp
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.PostLivelinessUseCase
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.GetEcDataUseCase
import com.example.dmoneyekyc.auth.di.RetrofitInstance1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SelfieModule {
    @Provides
    @Singleton

    fun provideLivelinessApiService(@RetrofitInstance1 retrofit: Retrofit): LivelinessApiService {
        return retrofit.create(LivelinessApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideSelfieApiService(@RetrofitInstance1 retrofit: Retrofit): SelfieApiService {
//        return retrofit.create(SelfieApiService::class.java)
//    }

    @Provides
    @Singleton
    fun provideSelfieRepository(livelinessApiService: LivelinessApiService): SelfieRepository {
        return SelfieRepositoryImp(livelinessApiService)
    }
    @Provides
    @Singleton
    fun provideGetEcDataUseCase(selfieRepository: SelfieRepository): GetEcDataUseCase {
        return GetEcDataUseCase(repository = selfieRepository)
    }
    @Provides
    @Singleton
    fun provideLivelinessUseCase(selfieRepository: SelfieRepository): PostLivelinessUseCase {
        return PostLivelinessUseCase(selfieRepository)
    }

}