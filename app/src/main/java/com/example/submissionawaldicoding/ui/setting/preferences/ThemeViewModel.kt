package com.example.submissionawaldicoding.ui.setting.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel(private val themePreferences: ThemePreferences) : ViewModel() {

    val themeSetting = themePreferences.themeSetting.asLiveData()

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            themePreferences.saveThemeSetting(isDarkMode)
        }
    }

    class Factory(private val themePreferences: ThemePreferences) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ThemeViewModel(themePreferences) as T
        }
    }
}