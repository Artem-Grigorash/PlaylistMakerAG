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
) : ViewModel()
 {

     companion object {
         fun getSharingViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
             initializer {
                 val sharingInteractor = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSettingsViewModel()
                 val settingsInteractor = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSharingViewModel()

                 SettingsViewModel(
                     settingsInteractor,
                     sharingInteractor
                 )
             }
         }
     }

     fun onThemeClicked(checked: Boolean, applicationContext: Context, sharedPref: SharedPreferences){
         settingsInteractor.updateThemeSetting(checked, applicationContext, sharedPref)
     }

    fun shareApp() : Intent{
        return sharingInteractor.shareApp()
    }
    fun openTerms() : Intent{
        return sharingInteractor.openTerms()
    }
    fun openSupport() : Intent{
        return sharingInteractor.openSupport()
    }
}