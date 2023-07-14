package com.example.playlistmakerag.settings.ui.view_models

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.playlistmakerag.R
import com.example.playlistmakerag.settings.ui.SettingsInteractor
import com.example.playlistmakerag.settings.ui.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

    fun shareApp(){
        sharingInteractor.shareApp()

        val shareIntent = Intent(Intent.ACTION_SEND)
        val url = R.string.practicum_url
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.type = "text/plain"
        startActivity(shareIntent)
    }
    fun openTerms(){
        sharingInteractor.openTerms()

        val url = Uri.parse(R.string.legal_url.toString())
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }
    fun openSupport(){
        sharingInteractor.openSupport()

        val message = R.string.thanks
        val massageTheme = R.string.subject
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.email))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, massageTheme)
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(shareIntent)
    }
}