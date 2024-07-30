package com.example.myapplication.features.login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val token: String = ""
)
