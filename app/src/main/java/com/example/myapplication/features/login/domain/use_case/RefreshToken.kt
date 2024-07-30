package com.example.myapplication.features.login.domain.use_case

import com.example.myapplication.common.Resource
import com.example.myapplication.core.util.domain.mapper.mapToDomainModel
import com.example.myapplication.features.login.domain.model.AccessToken
import com.example.myapplication.features.login.domain.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshToken @Inject constructor(
    private val repository: LoginRepository
) {

    operator fun invoke(
        refreshToken: JsonObject
    ): Flow<Resource<AccessToken>> = flow {
        emit(Resource.Loading())
        val response = repository.refreshToken(
            refreshToken
        )
        if (response.isSuccessful && response.code() == 200) {
            val token = response.body()?.mapToDomainModel() as AccessToken
            emit(Resource.Success(token))
        } else if (response.code() == 401) {
            emit(Resource.Error(response.code(), "Invalid refresh token"))
        } else {
            emit(Resource.Error(response.code(), response.message()))
        }
    }

}