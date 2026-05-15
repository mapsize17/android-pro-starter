package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.ItemResponse
import com.starter.core.network.PaginatedResponse

interface ItemRepository {
    suspend fun getItems(page: Int, limit: Int = 20): UiResult<PaginatedResponse<ItemResponse>>
}
