package com.example.playlistmakerag.settings.domain

import android.content.SharedPreferences

interface SwitchThemeInterface {
    fun updateThemeSetting(checked: Boolean)
    fun getChecked(): Boolean
}