package com.example.playlistmakerag.settings.domain

import android.content.Context
import android.content.SharedPreferences

interface SettingsInteractor {
    fun updateThemeSetting(checked: Boolean, applicationContext: Context, sharedPref: SharedPreferences)
}