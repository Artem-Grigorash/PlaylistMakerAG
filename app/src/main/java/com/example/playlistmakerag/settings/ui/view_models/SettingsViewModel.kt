package com.example.playlistmakerag.settings.ui.view_models

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.R
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.search.ui.view_models.SearchViewModel
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerag.settings.ui.SettingsInteractor
import com.example.playlistmakerag.settings.ui.SharingInteractor
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

    fun shareApp() : Intent{
        sharingInteractor.shareApp()

        val shareIntent = Intent(Intent.ACTION_SEND)
        val url = R.string.practicum_url
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.type = "text/plain"
        return shareIntent
    }
    fun openTerms() : Intent{
        sharingInteractor.openTerms()

        val url = Uri.parse(R.string.legal_url.toString())
        val intent = Intent(Intent.ACTION_VIEW, url)
        return intent
    }
    fun openSupport() : Intent{
        sharingInteractor.openSupport()

        val message = R.string.thanks
        val massageTheme = R.string.subject
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.email))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, massageTheme)
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        return shareIntent
    }
}