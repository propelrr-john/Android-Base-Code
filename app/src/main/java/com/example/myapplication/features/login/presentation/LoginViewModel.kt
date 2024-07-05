package com.example.myapplication.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Resource
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.features.login.domain.use_case.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
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
    private val loginUseCase: Login
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

}

sealed interface UIEvent {
    data object LoginSuccess: UIEvent
}