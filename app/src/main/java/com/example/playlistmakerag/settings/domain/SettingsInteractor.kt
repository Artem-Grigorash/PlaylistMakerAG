package com.example.playlistmakerag.settings.domain

interface SettingsInteractor {
    fun updateThemeSetting(
        checked: Boolean
    )

    fun getChecked() : Boolean
}