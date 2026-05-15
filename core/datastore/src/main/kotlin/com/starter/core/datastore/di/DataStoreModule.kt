package com.starter.core.datastore.di

import com.starter.core.datastore.AuthTokenStore
import com.starter.core.datastore.SettingsStore
import com.starter.core.datastore.UserPreferencesDataStore
import com.starter.core.network.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTokenProvider(prefs: UserPreferencesDataStore): TokenProvider {
        return object : TokenProvider {
            override suspend fun getAccessToken(): Flow<String?> = prefs.accessToken
            override suspend fun getRefreshToken(): String? = prefs.getRefreshToken()
            override suspend fun saveTokens(access: String, refresh: String) {
                prefs.saveTokens(access, refresh)
            }
            override suspend fun clearTokens() {
                prefs.clearTokens()
            }
        }
    }

    @Provides
    @Singleton
    fun provideSettingsStore(prefs: UserPreferencesDataStore): SettingsStore = prefs

    @Provides
    @Singleton
    fun provideAuthTokenStore(prefs: UserPreferencesDataStore): AuthTokenStore = prefs
}
