package com.example.myapplication.core.util.domain.mapper

import com.example.myapplication.features.login.data.remote.dto.AccessTokenDto
import com.example.myapplication.features.login.domain.model.AccessToken

fun AccessTokenDto.mapToDomainModel(): AccessToken {
    return AccessToken(
        token = token
    )
}