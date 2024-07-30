package com.example.myapplication.features.login.data.repository

import com.example.myapplication.features.login.data.remote.ApiService
import com.example.myapplication.features.login.data.remote.dto.AccessTokenDto
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.features.login.domain.repository.LoginRepository
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: ApiService
): LoginRepository {
    override suspend fun login(loginCredential: LoginCredential): Response<AccessTokenDto> {
        return api.login(loginCredential)
    }

    override suspend fun refreshToken(refreshToken: JsonObject): Response<AccessTokenDto> {
        return api.refreshToken(refreshToken)
    }
}