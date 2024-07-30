package com.example.myapplication.features.login.presentation

import com.example.myapplication.features.login.domain.model.LoginCredential

sealed class LoginAction {
    data class Authenticate(
        val loginCredential: LoginCredential
    ): LoginAction()

    object RefreshToken: LoginAction()
}