package com.example.playlistmakerag.settings.domain.impl

import com.example.playlistmakerag.settings.domain.SettingsInteractor
import com.example.playlistmakerag.settings.domain.SwitchThemeInterface

class SettingsInteractorImpl(private val switchTheme: SwitchThemeInterface) : SettingsInteractor {
    override fun updateThemeSetting(checked: Boolean) {
        switchTheme.updateThemeSetting(checked)
    }

    override fun getChecked() = switchTheme.getChecked()

}