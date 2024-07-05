package com.example.myapplication.features.login.presentation

import com.example.myapplication.features.login.domain.model.AccessToken

data class LoginState(
    val isLoading: Boolean = false,
    val accessToken: AccessToken? = null,
    val error: String = ""
)
