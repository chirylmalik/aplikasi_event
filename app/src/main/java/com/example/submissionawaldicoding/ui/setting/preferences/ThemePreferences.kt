package com.example.submissionawaldicoding.ui.setting.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class ThemePreferences(context: Context) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    private val dataStore = context.dataStore

    val themeSetting: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[themeKey] ?: false
        }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }
}