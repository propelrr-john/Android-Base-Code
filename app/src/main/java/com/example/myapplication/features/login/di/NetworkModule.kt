package com.example.myapplication.features.login.di

import com.example.myapplication.common.Constants
import com.example.myapplication.features.login.data.remote.ApiService
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(60, TimeUnit.MINUTES)
            readTimeout(60, TimeUnit.MINUTES)
            writeTimeout(60, TimeUnit.MINUTES)
        }.build()
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(Constants.BASE_URL)
        client(client)
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}