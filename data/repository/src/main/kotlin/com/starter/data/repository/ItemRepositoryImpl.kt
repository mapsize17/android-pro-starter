package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.ApiService
import com.starter.core.network.ItemResponse
import com.starter.core.network.NetworkResponse
import com.starter.core.network.PaginatedResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {

    override suspend fun getItems(page: Int, limit: Int): UiResult<PaginatedResponse<ItemResponse>> {
        return try {
            when (val response = apiService.getItems(page, limit)) {
                is NetworkResponse.Success -> UiResult.Success(response.data)
                is NetworkResponse.Error -> UiResult.Error(message = response.message)
            }
        } catch (e: Exception) {
            UiResult.Error(message = e.message ?: "Failed to load items", exception = e)
        }
    }
}
