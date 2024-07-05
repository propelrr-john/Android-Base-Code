package com.example.myapplication.features.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AccessTokenDto(
    @SerializedName("token")
    val token: String
)
