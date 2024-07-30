package com.example.myapplication.core.util.data.local

import android.content.Context
import androidx.datastore.dataStore
import com.example.myapplication.core.util.domain.serializer.AccessTokenSerializer
import com.example.myapplication.features.login.domain.model.AccessToken
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val context: Context
){

    private val Context.accessTokenDataStore by dataStore("access_token.json", AccessTokenSerializer)
    private val accessTokenDataStore = context.accessTokenDataStore

    suspend fun updateAccessToken(accessToken: AccessToken) {
        accessTokenDataStore.updateData {
            it.copy(
                token = accessToken.token
            )
        }
    }

    suspend fun getAccessToken(): String {
        return accessTokenDataStore.data.single().token
    }

}