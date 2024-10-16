package com.example.dmoneyekyc.Screen.NIDScanning.di


import com.example.dmoneyekyc.Screen.NIDScanning.data.NidOCRApiService
import com.example.dmoneyekyc.Screen.NIDScanning.data.OcrRepositoryImp
import com.example.dmoneyekyc.Screen.NIDScanning.domain.repository.OcrRepository
import com.example.dmoneyekyc.Screen.NIDScanning.domain.usecase.GetNidOcrUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NidScanModule {

    @Provides
    @Singleton
    fun provideNidScanOCRApiService( retrofit: Retrofit): NidOCRApiService {
        return retrofit.create(NidOCRApiService::class.java)
    }


//    @Provides
//    @Singleton
//    fun provideNIDPostApiService(@RetrofitInstance1 retrofit: Retrofit):NidPostApiService{
//        return retrofit.create(NidPostApiService::class.java)
//    }

    @Provides
    @Singleton
    fun provideNidOcrRepository(nidOCRApiService: NidOCRApiService): OcrRepository {
        return  OcrRepositoryImp(nidOCRApiService)
    }
//    @Provides
//    @Singleton
//    fun provideNidPostRepository(nidPostApiService: NidPostApiService):NidRepository{
//        return  NidRepositoryImp(nidPostApiService)
//    }
//    @Provides
//    @Singleton
//    fun provideNidPostUseCase(nidRepository: NidRepository):PostNidUseCase{
//        return PostNidUseCase(nidRepository)
//    }
    @Provides
    @Singleton
    fun provideNidOCRUseCase(ocrRepository: OcrRepository): GetNidOcrUseCase {
        return GetNidOcrUseCase(ocrRepository)
    }


}