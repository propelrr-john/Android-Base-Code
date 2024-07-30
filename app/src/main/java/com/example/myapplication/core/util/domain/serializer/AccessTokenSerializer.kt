package com.example.myapplication.core.util.domain.serializer

import androidx.datastore.core.Serializer
import com.example.myapplication.features.login.domain.model.AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AccessTokenSerializer: Serializer<AccessToken> {
    override val defaultValue: AccessToken
        get() = AccessToken()

    override suspend fun readFrom(input: InputStream): AccessToken {
        return try {
            Json.decodeFromString(
                deserializer = AccessToken.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AccessToken, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = AccessToken.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}