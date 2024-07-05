package com.example.myapplication.features.login.data.remote

import com.example.myapplication.features.login.data.remote.dto.AccessTokenDto
import com.example.myapplication.features.login.domain.model.LoginCredential
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginCredential: LoginCredential
    ): Response<AccessTokenDto>

}