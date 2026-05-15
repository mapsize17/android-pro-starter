package com.starter.core.network

import kotlinx.coroutines.flow.Flow

sealed interface NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error(val message: String, val code: Int = -1) : NetworkResponse<Nothing>
}
