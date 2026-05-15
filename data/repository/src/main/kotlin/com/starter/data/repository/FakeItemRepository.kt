package com.starter.data.repository

import com.starter.core.model.result.UiResult
import com.starter.core.network.ItemResponse
import com.starter.core.network.PaginatedResponse

class FakeItemRepository : ItemRepository {
    private var shouldSucceed = true

    fun setShouldSucceed(succeed: Boolean) { shouldSucceed = succeed }

    override suspend fun getItems(page: Int, limit: Int): UiResult<PaginatedResponse<ItemResponse>> {
        return if (shouldSucceed) {
            UiResult.Success(
                PaginatedResponse(
                    items = listOf(
                        ItemResponse("1", "Item 1", "Description 1", null),
                        ItemResponse("2", "Item 2", "Description 2", null)
                    ),
                    page = page,
                    totalPages = 1,
                    totalItems = 2
                )
            )
        } else {
            UiResult.Error(message = "Failed to load items")
        }
    }
}
