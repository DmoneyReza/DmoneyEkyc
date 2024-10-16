package com.example.dmoney.di

import android.content.Context
import com.example.dmoney.auth.util.DeviceIdManager
import com.example.dmoney.util.ConnectivityObserver
import com.example.dmoney.util.LocalStorageService
import com.example.dmoney.util.DmNetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLocalStorage(@ApplicationContext context: Context): LocalStorageService {
        return LocalStorageService(context)
    }

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context): ConnectivityObserver {
        return DmNetworkConnectivityObserver(context)
    }
    @Provides
    @Singleton
    fun provideLocation(@ApplicationContext context: Context): DeviceIdManager {
        return DeviceIdManager(context)
    }

}