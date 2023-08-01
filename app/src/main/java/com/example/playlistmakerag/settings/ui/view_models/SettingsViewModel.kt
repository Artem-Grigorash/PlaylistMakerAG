package com.example.playlistmakerag.settings.ui.view_models

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.settings.domain.SettingsInteractor
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.example.playlistmakerag.sharing.domain.impl.SharingInteractorImpl

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

    companion object {
        fun getSharingViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val sharingInteractor =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSettingsViewModel()
                val settingsInteractor =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSharingViewModel()

                SettingsViewModel(
                    settingsInteractor,
                    sharingInteractor
                )
            }
        }
    }

    fun getChecked(sharedPref: SharedPreferences) = settingsInteractor.getChecked(sharedPref)

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return settingsInteractor.provideSharedPreferences(context)
    }

    fun onThemeClicked(
        checked: Boolean,
        applicationContext: Context,
        sharedPref: SharedPreferences
    ) {
        settingsInteractor.updateThemeSetting(checked, applicationContext, sharedPref)
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