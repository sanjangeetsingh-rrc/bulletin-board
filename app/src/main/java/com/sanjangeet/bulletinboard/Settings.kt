package com.sanjangeet.bulletinboard

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object DarkModeManager {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    fun isDarkMode(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    suspend fun toggleDarkMode(context: Context) {
        context.dataStore.edit { prefs ->
            val current = prefs[DARK_MODE_KEY] ?: false
            prefs[DARK_MODE_KEY] = !current
        }
    }
}
