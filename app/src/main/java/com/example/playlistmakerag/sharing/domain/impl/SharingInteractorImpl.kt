package com.example.playlistmakerag.sharing.domain.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmakerag.R
import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.example.playlistmakerag.sharing.data.EmailData
import com.example.playlistmakerag.sharing.data.ExternalNavigator

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {
    override fun shareApp(context: Context): Intent {
        return externalNavigator.shareLink(getShareAppLink(context))
    }

    override fun openTerms(context: Context): Intent {
        return externalNavigator.openLink(getTermsLink(context))
    }

    override fun openSupport(context: Context): Intent {
        return externalNavigator.openEmail(getSupportEmailData(context))
    }

    private fun getShareAppLink(context: Context): String {
        return context.getString(R.string.practicum_url)
    }

    private fun getSupportEmailData(context: Context): EmailData {
        val message = context.getString(R.string.thanks)
        val massageTheme = context.getString(R.string.subject)
        return EmailData(message, massageTheme)
    }

    private fun getTermsLink(context: Context): Uri {
        return Uri.parse(context.getString(R.string.legal_url))
    }
}