package com.example.playlistmakerag.settings.ui.view_models

import androidx.lifecycle.ViewModel
import com.example.playlistmakerag.settings.ui.SettingsInteractor
import com.example.playlistmakerag.settings.ui.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    // Основной код
}