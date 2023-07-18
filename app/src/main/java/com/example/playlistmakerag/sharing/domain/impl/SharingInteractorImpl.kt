package com.example.playlistmakerag.sharing.domain.impl

import android.content.Intent
import android.net.Uri
import com.example.playlistmakerag.R
import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.example.playlistmakerag.sharing.data.EmailData
import com.example.playlistmakerag.sharing.data.ExternalNavigator

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor
{
    override fun shareApp() : Intent{
        return externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() : Intent {
        return externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() : Intent{
        return externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): Int {
        return R.string.practicum_url
    }

    private fun getSupportEmailData(): EmailData {
        val message = R.string.thanks
        val massageTheme = R.string.subject
        return EmailData(message, massageTheme)
    }

    private fun getTermsLink(): Uri {
        return Uri.parse(R.string.legal_url.toString())
    }
}