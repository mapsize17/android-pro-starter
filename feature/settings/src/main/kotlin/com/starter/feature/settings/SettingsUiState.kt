package com.starter.feature.settings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(
        val isDarkMode: Boolean = false,
        val userName: String = "User"
    ) : SettingsUiState
}
