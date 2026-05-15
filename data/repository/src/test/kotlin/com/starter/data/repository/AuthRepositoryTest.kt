package com.starter.data.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class AuthRepositoryTest {

    @Test
    fun `FakeAuthRepository login returns success`() = runTest {
        val repo = FakeAuthRepository()
        val result = repo.login("test@test.com", "pass123")

        assertThat(result).isInstanceOf(com.starter.core.model.result.UiResult.Success::class.java)
    }

    @Test
    fun `FakeAuthRepository login with failure returns error`() = runTest {
        val repo = FakeAuthRepository().apply { setShouldSucceed(false) }
        val result = repo.login("test@test.com", "wrong")

        assertThat(result).isInstanceOf(com.starter.core.model.result.UiResult.Error::class.java)
    }

    @Test
    fun `FakeAuthRepository isLoggedIn after login returns true`() = runTest {
        val repo = FakeAuthRepository()
        repo.login("test@test.com", "pass")
        assertThat(repo.isLoggedIn()).isTrue()
    }

    @Test
    fun `FakeAuthRepository logout sets isLoggedIn false`() = runTest {
        val repo = FakeAuthRepository()
        repo.login("test@test.com", "pass")
        repo.logout()
        assertThat(repo.isLoggedIn()).isFalse()
    }

    @Test
    fun `FakeItemRepository getItems returns items`() = runTest {
        val repo = FakeItemRepository()
        val result = repo.getItems(0)

        assertThat(result).isInstanceOf(com.starter.core.model.result.UiResult.Success::class.java)
    }
}
