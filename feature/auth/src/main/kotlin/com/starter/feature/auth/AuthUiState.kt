package com.starter.feature.auth

import com.starter.core.network.AuthResponse

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data class Login(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false
    ) : AuthUiState

    data class SignUp(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false
    ) : AuthUiState

    data class Authenticated(val auth: AuthResponse) : AuthUiState
    data class Error(val message: String) : AuthUiState
}
