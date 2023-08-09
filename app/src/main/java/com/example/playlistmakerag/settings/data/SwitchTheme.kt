package com.example.playlistmakerag.settings.data

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.app.DARK_THEME_KEY
import com.example.playlistmakerag.settings.domain.SwitchThemeInterface

class SwitchTheme(private val sharedPref: SharedPreferences, private val context: Context): SwitchThemeInterface {

    override fun updateThemeSetting(
        checked: Boolean,
    ) {
        (context as App).switchTheme(checked)
        sharedPref.edit()
            .putBoolean(DARK_THEME_KEY, checked)
            .apply()
    }
    override fun getChecked() = sharedPref.getBoolean(DARK_THEME_KEY, false)
}