package com.starter.core.network

import kotlinx.coroutines.flow.Flow

interface TokenProvider {
    suspend fun getAccessToken(): Flow<String?>
    suspend fun getRefreshToken(): String?
    suspend fun saveTokens(access: String, refresh: String)
    suspend fun clearTokens()
}
