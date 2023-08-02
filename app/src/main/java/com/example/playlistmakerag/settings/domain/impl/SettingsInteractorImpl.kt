package com.example.playlistmakerag.settings.domain.impl

import com.example.playlistmakerag.settings.data.SwitchTheme
import com.example.playlistmakerag.settings.domain.SettingsInteractor

class SettingsInteractorImpl(private val switchTheme: SwitchTheme) : SettingsInteractor {
    override fun updateThemeSetting(checked: Boolean) {
        switchTheme.updateThemeSetting(checked)
    }

    override fun getChecked() = switchTheme.getChecked()

}