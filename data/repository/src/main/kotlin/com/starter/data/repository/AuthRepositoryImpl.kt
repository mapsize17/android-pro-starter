package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.ApiService
import com.starter.core.network.LoginRequest
import com.starter.core.network.NetworkResponse
import com.starter.core.network.RegisterRequest
import com.starter.core.datastore.UserPreferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: UserPreferencesDataStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): UiResult<AuthResponse> {
        return try {
            when (val response = apiService.login(LoginRequest(email, password))) {
                is NetworkResponse.Success -> {
                    prefs.saveTokens(response.data.accessToken, response.data.refreshToken)
                    UiResult.Success(response.data)
                }
                is NetworkResponse.Error -> UiResult.Error(
                    message = response.message
                )
            }
        } catch (e: Exception) {
            UiResult.Error(message = e.message ?: "Login failed", exception = e)
        }
    }

    override suspend fun register(email: String, password: String): UiResult<AuthResponse> {
        return try {
            when (val response = apiService.register(RegisterRequest(email, password))) {
                is NetworkResponse.Success -> {
                    prefs.saveTokens(response.data.accessToken, response.data.refreshToken)
                    UiResult.Success(response.data)
                }
                is NetworkResponse.Error -> UiResult.Error(
                    message = response.message
                )
            }
        } catch (e: Exception) {
            UiResult.Error(message = e.message ?: "Registration failed", exception = e)
        }
    }

    override suspend fun logout() {
        prefs.clearTokens()
    }

    override suspend fun isLoggedIn(): Boolean {
        return prefs.getAccessToken() != null
    }
}
