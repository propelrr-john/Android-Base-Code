package com.example.myapplication.features.login.data.repository

import com.example.myapplication.features.login.data.remote.ApiService
import com.example.myapplication.features.login.data.remote.dto.AccessTokenDto
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.features.login.domain.repository.LoginRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: ApiService
): LoginRepository {
    override suspend fun login(loginCredential: LoginCredential): Response<AccessTokenDto> {
        return api.login(loginCredential)
    }
}