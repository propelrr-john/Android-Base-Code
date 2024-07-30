package com.example.myapplication.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Resource
import com.example.myapplication.core.util.data.local.DataStoreManager
import com.example.myapplication.features.login.domain.model.AccessToken
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.features.login.domain.use_case.Login
import com.example.myapplication.features.login.domain.use_case.RefreshToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: Login,
    private val refreshTokenUseCase: RefreshToken,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<UIEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Authenticate -> {
                login(action.loginCredential)
            }
            is LoginAction.RefreshToken -> {
                refreshToken()
            }
        }
    }

    private fun login(
        loginCredential: LoginCredential
    ) = viewModelScope.launch(
        CoroutineExceptionHandler { _, exception ->
            runBlocking {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.localizedMessage ?: "An unexpected error occurred!"
                    )
                }
            }
        }
    ) {
        loginUseCase(loginCredential).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            accessToken = null,
                            error = ""
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.let { accessToken ->
                        dataStoreManager.updateAccessToken(
                            accessToken
                        )
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accessToken = result.data,
                            error = ""
                        )
                    }
                    _eventChannel.trySend(
                        UIEvent.LoginSuccess
                    )
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accessToken = null,
                            error = result.message ?: "An unexpected error occurred!"
                        )
                    }
                }
            }
        }
    }

    private fun refreshToken() = viewModelScope.launch(
        CoroutineExceptionHandler { _, exception ->
            runBlocking {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.localizedMessage ?: "An unexpected error occurred!"
                    )
                }
            }
        }
    ) {

        val jsonString = "{\n" +
                "  \"token\": \"${dataStoreManager.getAccessToken()}\"\n" +
                "}"
        refreshTokenUseCase(
            Gson().fromJson(jsonString, JsonObject::class.java)
        ).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            accessToken = null,
                            error = ""
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.let { accessToken ->
                        dataStoreManager.updateAccessToken(
                            accessToken
                        )
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accessToken = result.data,
                            error = ""
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accessToken = null,
                            error = result.message ?: "An unexpected error occurred!"
                        )
                    }
                }
            }
        }
    }

    suspend fun getAccessToken(): String {
        return dataStoreManager.getAccessToken()
    }

}

sealed interface UIEvent {
    data object LoginSuccess: UIEvent
}