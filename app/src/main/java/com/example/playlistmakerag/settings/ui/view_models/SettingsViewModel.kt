package com.example.playlistmakerag.settings.ui.view_models

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerag.sharing.domain.impl.SharingInteractorImpl

class SettingsViewModel(
    private val sharingInteractor: SharingInteractorImpl,
    private val settingsInteractor: SettingsInteractorImpl,
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

    fun shareApp(context: Context): Intent {
        return sharingInteractor.shareApp(context)
    }

    fun openTerms(context: Context): Intent {
        return sharingInteractor.openTerms(context)
    }

    fun openSupport(context: Context): Intent {
        return sharingInteractor.openSupport(context)
    }
}