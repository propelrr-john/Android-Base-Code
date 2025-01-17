package com.example.myapplication.features.login.domain.repository

import com.example.myapplication.features.login.data.remote.dto.AccessTokenDto
import com.example.myapplication.features.login.domain.model.LoginCredential
import retrofit2.Response

interface LoginRepository {

    suspend fun login(
        loginCredential: LoginCredential
    ): Response<AccessTokenDto>

}