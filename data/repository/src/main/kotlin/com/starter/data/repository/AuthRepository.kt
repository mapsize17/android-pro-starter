package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.AuthResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): UiResult<AuthResponse>
    suspend fun register(email: String, password: String): UiResult<AuthResponse>
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
