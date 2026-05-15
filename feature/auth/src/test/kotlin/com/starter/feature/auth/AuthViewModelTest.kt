package com.starter.feature.auth

import app.cash.turbine.test
import com.starter.data.repository.FakeAuthRepository
import com.starter.core.testing.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class AuthViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `login with valid credentials emits Authenticated`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.login("test@example.com", "password123")

        viewModel.uiState.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(AuthUiState.Login::class.java)
            assertThat((loading as AuthUiState.Login).isLoading).isTrue()

            val authenticated = awaitItem()
            assertThat(authenticated).isInstanceOf(AuthUiState.Authenticated::class.java)
        }
    }

    @Test
    fun `login with blank email emits Error`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.login("", "password123")

        viewModel.uiState.test {
            val error = awaitItem()
            assertThat(error).isInstanceOf(AuthUiState.Error::class.java)
            assertThat((error as AuthUiState.Error).message).contains("required")
        }
    }

    @Test
    fun `login with blank password emits Error`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.login("test@test.com", "")

        viewModel.uiState.test {
            val error = awaitItem()
            assertThat(error).isInstanceOf(AuthUiState.Error::class.java)
        }
    }

    @Test
    fun `signUp with mismatched passwords emits Error`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.signUp("test@test.com", "pass123", "pass456")

        viewModel.uiState.test {
            val error = awaitItem()
            assertThat(error).isInstanceOf(AuthUiState.Error::class.java)
            assertThat((error as AuthUiState.Error).message).contains("do not match")
        }
    }

    @Test
    fun `signUp with blank fields emits Error`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.signUp("", "pass123", "pass123")

        viewModel.uiState.test {
            val error = awaitItem()
            assertThat(error).isInstanceOf(AuthUiState.Error::class.java)
        }
    }

    @Test
    fun `login with invalid credentials from repo emits Error`() = runTest {
        val repo = FakeAuthRepository().apply { setShouldSucceed(false) }
        val viewModel = AuthViewModel(repo)

        viewModel.login("wrong@test.com", "wrongpass")

        viewModel.uiState.test {
            awaitItem() // Login loading
            val error = awaitItem()
            assertThat(error).isInstanceOf(AuthUiState.Error::class.java)
            assertThat((error as AuthUiState.Error).message).contains("Invalid")
        }
    }

    @Test
    fun `resetState returns to Idle`() {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.resetState()

        assertThat(viewModel.uiState.value).isEqualTo(AuthUiState.Idle)
    }
}
