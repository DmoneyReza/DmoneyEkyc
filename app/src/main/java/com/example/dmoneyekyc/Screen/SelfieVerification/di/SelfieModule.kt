package com.example.dmoneyekyc.Screen.SelfieVerification.di
import com.example.dmoneyekyc.Screen.SelfieVerification.data.EcApiService
import com.example.dmoneyekyc.Screen.SelfieVerification.data.LivelinessApiService
import com.example.dmoneyekyc.Screen.SelfieVerification.data.SelfieRepositoryImp
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.PostLivelinessUseCase
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.SelfieRepository
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.usecase.GetEcDataUseCase
import com.example.dmoneyekyc.auth.di.RetrofitInstance1
import com.example.dmoneyekyc.auth.di.RetrofitInstance2
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

    @Provides
    @Singleton
    fun provideSelfieApiService(@RetrofitInstance2 retrofit: Retrofit): EcApiService {
        return retrofit.create(EcApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSelfieRepository(livelinessApiService: LivelinessApiService,ecApiService: EcApiService): SelfieRepository {
        return SelfieRepositoryImp(livelinessApiService,ecApiService)
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