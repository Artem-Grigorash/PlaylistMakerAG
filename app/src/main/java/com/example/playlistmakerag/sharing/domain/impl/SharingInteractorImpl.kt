package com.example.playlistmakerag.sharing.domain.impl

import android.content.Intent
import android.net.Uri
import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.example.playlistmakerag.sharing.data.EmailData
import com.example.playlistmakerag.sharing.domain.ExternalNavigatorInterface

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigatorInterface,
) : SharingInteractor {
    override fun shareApp(link: String): Intent {
        return externalNavigator.shareLink(getShareAppLink(link))
    }

    override fun openTerms(link: String): Intent {
        return externalNavigator.openLink(getTermsLink(link))
    }

    override fun openSupport(message: String, massageTheme: String): Intent {
        return externalNavigator.openEmail(getSupportEmailData(message, massageTheme))
    }

    private fun getShareAppLink(link: String): String {
        return link
    }

    private fun getSupportEmailData(message: String, massageTheme: String): EmailData {
        return EmailData(message, massageTheme)
    }

    private fun getTermsLink(link : String): Uri {
        return Uri.parse(link)
    }
}