package com.starter.feature.home

import com.starter.core.network.ItemResponse

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val items: List<ItemResponse>, val page: Int = 0, val hasMore: Boolean = false) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
