package com.example.myapplication.features.login.di

import com.example.myapplication.features.login.data.remote.ApiService
import com.example.myapplication.features.login.data.repository.LoginRepositoryImpl
import com.example.myapplication.features.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideApiService(api: ApiService): LoginRepository {
        return LoginRepositoryImpl(api)
    }

}