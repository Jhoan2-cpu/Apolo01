package com.apolo.tracking.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.apolo.tracking.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val TOKEN_VALUE = stringPreferencesKey("token_value")
        val TOKEN_EXPIRED = stringPreferencesKey("token_expired")
    }

    val tokenFlow: Flow<AuthToken?> = dataStore.data.map { prefs ->
        val value = prefs[Keys.TOKEN_VALUE]
        val expired = prefs[Keys.TOKEN_EXPIRED]
        if (!value.isNullOrBlank()) AuthToken(tokenValue = value, tokenExpired = expired ?: "")
        else null
    }

    val isSessionActive: Flow<Boolean> = tokenFlow.map { it != null }

    suspend fun saveToken(tokenValue: String, tokenExpired: String) {
        dataStore.edit { prefs ->
            prefs[Keys.TOKEN_VALUE] = tokenValue
            prefs[Keys.TOKEN_EXPIRED] = tokenExpired
        }
    }

    suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}
