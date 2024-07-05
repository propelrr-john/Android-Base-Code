package com.example.myapplication.features.login.domain.use_case

import com.example.myapplication.common.Resource
import com.example.myapplication.features.login.domain.model.AccessToken
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.features.login.domain.repository.LoginRepository
import com.example.myapplication.features.login.presentation.util.mapToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Login @Inject constructor(
    private val repository: LoginRepository
) {

    operator fun invoke(
        loginCredential: LoginCredential
    ): Flow<Resource<AccessToken>> = flow {
        emit(Resource.Loading())
        val response = repository.login(
            loginCredential
        )
        if (response.isSuccessful && response.code() == 200) {
            val token = response.body()?.mapToDomainModel() as AccessToken
            emit(Resource.Success(token))
        } else if (response.code() == 401) {
            emit(Resource.Error(response.code(), "Incorrect username or password"))
        } else {
            emit(Resource.Error(response.code(), response.message()))
        }
    }

}