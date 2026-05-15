package com.starter.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.prefsDataStore by preferencesDataStore(name = "starter_preferences")

@Singleton
open class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsStore, AuthTokenStore {
    companion object {
        private val KEY_IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        private val KEY_IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    val isOnboarded: Flow<Boolean> = context.prefsDataStore.data.map { it[KEY_IS_ONBOARDED] ?: false }
    val isDarkMode: Flow<Boolean> = context.prefsDataStore.data.map { it[KEY_IS_DARK_MODE] ?: false }
    val accessToken: Flow<String?> = context.prefsDataStore.data.map { it[KEY_ACCESS_TOKEN] }

    suspend fun getAccessToken(): String? {
        return context.prefsDataStore.data.map { it[KEY_ACCESS_TOKEN] }
    }

    suspend fun getRefreshToken(): String? {
        return context.prefsDataStore.data.map { it[KEY_REFRESH_TOKEN] }
    }

    suspend fun setOnboarded(value: Boolean) {
        context.prefsDataStore.edit { it[KEY_IS_ONBOARDED] = value }
    }

    suspend fun setDarkMode(value: Boolean) {
        context.prefsDataStore.edit { it[KEY_IS_DARK_MODE] = value }
    }

    suspend fun saveTokens(access: String, refresh: String) {
        context.prefsDataStore.edit {
            it[KEY_ACCESS_TOKEN] = access
            it[KEY_REFRESH_TOKEN] = refresh
        }
    }

    suspend fun clearTokens() {
        context.prefsDataStore.edit {
            it.remove(KEY_ACCESS_TOKEN)
            it.remove(KEY_REFRESH_TOKEN)
        }
    }
}
