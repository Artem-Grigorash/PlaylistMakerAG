package com.example.playlistmakerag.settings.ui.view_models

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.playlistmakerag.settings.domain.SettingsInteractor
import com.example.playlistmakerag.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {


    fun getChecked() = settingsInteractor.getChecked()

    fun onThemeClicked(checked: Boolean) {
        settingsInteractor.updateThemeSetting(checked)
    }

    fun shareApp(link: String): Intent {
        return sharingInteractor.shareApp(link)
    }

    fun openTerms(link: String): Intent {
        return sharingInteractor.openTerms(link)
    }

    fun openSupport(message: String, massageTheme: String): Intent {
        return sharingInteractor.openSupport(message, massageTheme)
    }
}