package com.starter.core.model.result

sealed interface UiResult<out T> {
    data object Loading : UiResult<Nothing>
    data class Success<T>(val data: T) : UiResult<T>
    data class Error(val exception: Throwable? = null, val message: String? = null) : UiResult<Nothing>
}
