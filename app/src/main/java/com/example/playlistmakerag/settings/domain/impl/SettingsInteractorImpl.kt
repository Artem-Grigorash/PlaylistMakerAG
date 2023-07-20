package com.example.playlistmakerag.settings.domain.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmakerag.settings.data.SwitchTheme
import com.example.playlistmakerag.settings.domain.SettingsInteractor

class SettingsInteractorImpl(private val switchTheme: SwitchTheme): SettingsInteractor {
    override fun updateThemeSetting(checked: Boolean, applicationContext: Context, sharedPref: SharedPreferences) {
        switchTheme.updateThemeSetting(checked, applicationContext, sharedPref)
    }
}