package com.starter.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starter.core.model.result.UiResult
import com.starter.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Email and password are required")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.Login(email, password, isLoading = true)
            when (val result = authRepository.login(email, password)) {
                is UiResult.Success -> _uiState.value = AuthUiState.Authenticated(result.data)
                is UiResult.Error -> _uiState.value = AuthUiState.Error(result.message)
                is UiResult.Loading -> {}
            }
        }
    }

    fun signUp(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("All fields are required")
            return
        }
        if (password != confirmPassword) {
            _uiState.value = AuthUiState.Error("Passwords do not match")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.SignUp(email, password, confirmPassword, isLoading = true)
            when (val result = authRepository.register(email, password)) {
                is UiResult.Success -> _uiState.value = AuthUiState.Authenticated(result.data)
                is UiResult.Error -> _uiState.value = AuthUiState.Error(result.message)
                is UiResult.Loading -> {}
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}
