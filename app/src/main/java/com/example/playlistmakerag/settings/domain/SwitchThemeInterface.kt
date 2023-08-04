package com.example.playlistmakerag.settings.domain

interface SwitchThemeInterface {
    fun updateThemeSetting(checked: Boolean)
    fun getChecked(): Boolean
}