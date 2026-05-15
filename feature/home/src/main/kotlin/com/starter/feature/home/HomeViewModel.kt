package com.starter.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starter.core.model.result.UiResult
import com.starter.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var hasMore = true

    init {
        loadItems()
    }

    fun loadItems() {
        currentPage = 0
        hasMore = true
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            when (val result = itemRepository.getItems(page = 0)) {
                is UiResult.Success -> {
                    val items = result.data.items
                    hasMore = result.data.page < result.data.totalPages - 1
                    _uiState.value = HomeUiState.Success(items = items, page = 0, hasMore = hasMore)
                }
                is UiResult.Error -> _uiState.value = HomeUiState.Error(result.message)
                is UiResult.Loading -> {}
            }
        }
    }

    fun loadMore() {
        if (!hasMore || _uiState.value !is HomeUiState.Success) return
        val nextPage = currentPage + 1
        viewModelScope.launch {
            when (val result = itemRepository.getItems(page = nextPage)) {
                is UiResult.Success -> {
                    val current = (_uiState.value as HomeUiState.Success)
                    hasMore = result.data.page < result.data.totalPages - 1
                    currentPage = nextPage
                    _uiState.value = current.copy(
                        items = current.items + result.data.items,
                        page = nextPage,
                        hasMore = hasMore
                    )
                }
                is UiResult.Error -> { /* silently fail on load more */ }
                is UiResult.Loading -> {}
            }
        }
    }
}
