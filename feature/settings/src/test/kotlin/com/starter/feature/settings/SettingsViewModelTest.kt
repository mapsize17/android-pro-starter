package com.starter.feature.settings

import app.cash.turbine.test
import com.starter.core.datastore.SettingsStore
import com.starter.data.repository.FakeAuthRepository
import com.starter.core.testing.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class FakeSettingsStore(private val initialDarkMode: Boolean = false) : SettingsStore {
    private val _isDarkMode = MutableStateFlow(initialDarkMode)
    override val isDarkMode: Flow<Boolean> = _isDarkMode
    override suspend fun setDarkMode(enabled: Boolean) { _isDarkMode.value = enabled }
}

class SettingsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `initial state is Loading then Success`() = runTest {
        val repo = FakeAuthRepository()
        val store = FakeSettingsStore()
        val viewModel = SettingsViewModel(repo, store)

        viewModel.uiState.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(SettingsUiState.Loading::class.java)

            val success = awaitItem()
            assertThat(success).isInstanceOf(SettingsUiState.Success::class.java)
            assertThat((success as SettingsUiState.Success).isDarkMode).isFalse()
        }
    }

    @Test
    fun `logout sets loggedOut to true`() = runTest {
        val repo = FakeAuthRepository().apply { login("test@test.com", "pass") }
        val store = FakeSettingsStore()
        val viewModel = SettingsViewModel(repo, store)

        viewModel.logout()

        viewModel.loggedOut.test {
            val loggedOut = awaitItem()
            assertThat(loggedOut).isTrue()
        }
    }
}
