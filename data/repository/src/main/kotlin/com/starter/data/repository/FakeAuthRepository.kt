package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.AuthResponse

class FakeAuthRepository : AuthRepository {
    private var shouldSucceed = true
    private var throwException = false
    private var loggedIn = false

    fun setShouldSucceed(succeed: Boolean) {
        shouldSucceed = succeed
    }

    fun setThrowException(throwIt: Boolean) {
        throwException = throwIt
    }

    override suspend fun login(email: String, password: String): UiResult<AuthResponse> {
        if (throwException) throw RuntimeException("Network error")
        return if (shouldSucceed) {
            loggedIn = true
            UiResult.Success(
                AuthResponse(
                    accessToken = "fake_access_token",
                    refreshToken = "fake_refresh_token",
                    user = com.starter.core.network.UserProfileResponse(
                        id = "1", email = email, name = "Test User"
                    )
                )
            )
        } else {
            UiResult.Error(message = "Invalid credentials")
        }
    }

    override suspend fun register(email: String, password: String): UiResult<AuthResponse> {
        return login(email, password)
    }

    override suspend fun logout() {
        loggedIn = false
    }

    override suspend fun isLoggedIn(): Boolean = loggedIn
}
