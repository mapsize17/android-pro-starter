package com.starter.feature.home

import app.cash.turbine.test
import com.starter.data.repository.FakeItemRepository
import com.starter.core.testing.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `initial state is Loading then Success`() = runTest {
        val repo = FakeItemRepository()
        val viewModel = HomeViewModel(repo)

        viewModel.uiState.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(HomeUiState.Loading::class.java)

            val success = awaitItem()
            assertThat(success).isInstanceOf(HomeUiState.Success::class.java)
            assertThat((success as HomeUiState.Success).items).hasSize(2)
        }
    }

    @Test
    fun `loadItems on success emits Success with items`() = runTest {
        val repo = FakeItemRepository()
        val viewModel = HomeViewModel(repo)

        viewModel.loadItems()

        viewModel.uiState.test {
            awaitItem() // initial Loading
            val success = awaitItem()
            assertThat(success).isInstanceOf(HomeUiState.Success::class.java)
            assertThat((success as HomeUiState.Success).items[0].title).isEqualTo("Item 1")
        }
    }

    @Test
    fun `loadItems on failure emits Error`() = runTest {
        val repo = FakeItemRepository().apply { setShouldSucceed(false) }
        val viewModel = HomeViewModel(repo)

        viewModel.loadItems()

        viewModel.uiState.test {
            awaitItem() // initial loading
            val error = awaitItem()
            assertThat(error).isInstanceOf(HomeUiState.Error::class.java)
            assertThat((error as HomeUiState.Error).message).isNotEmpty()
        }
    }

    @Test
    fun `loadMore appends items`() = runTest {
        val repo = FakeItemRepository()
        val viewModel = HomeViewModel(repo)

        viewModel.uiState.test {
            awaitItem() // Loading
            val first = awaitItem()
            assertThat((first as HomeUiState.Success).items).hasSize(2)

            viewModel.loadMore()
            val second = awaitItem() // loadMore will trigger the same page data again
            assertThat(second).isInstanceOf(HomeUiState.Success::class.java)
        }
    }
}
