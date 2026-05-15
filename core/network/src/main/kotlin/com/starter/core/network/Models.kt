package com.starter.core.network

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenRefreshAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val apiService: Lazy<ApiService>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = tokenProvider.getRefreshToken() ?: return@runBlocking null
            val result = apiService.get().refreshToken(RefreshRequest(refreshToken))
            when (result) {
                is NetworkResponse.Success -> {
                    tokenProvider.saveTokens(result.data.accessToken, result.data.refreshToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${result.data.accessToken}")
                        .build()
                }
                is NetworkResponse.Error -> {
                    tokenProvider.clearTokens()
                    null
                }
            }
        }
    }
}

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val password: String)
data class RefreshRequest(val refreshToken: String)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserProfileResponse
)

data class UserProfileResponse(
    val id: String,
    val email: String,
    val name: String
)

data class ItemResponse(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?
)

data class PaginatedResponse<T>(
    val items: List<T>,
    val page: Int,
    val totalPages: Int,
    val totalItems: Long
)
