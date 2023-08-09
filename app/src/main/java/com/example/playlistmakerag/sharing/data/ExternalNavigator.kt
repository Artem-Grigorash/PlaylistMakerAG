package com.example.playlistmakerag.sharing.data

import android.content.Intent
import android.net.Uri
import com.example.playlistmakerag.R
import com.example.playlistmakerag.sharing.domain.ExternalNavigatorInterface

class ExternalNavigator: ExternalNavigatorInterface {
    override fun shareLink(link: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        shareIntent.type = "text/plain"
        return shareIntent
    }

    override fun openLink(link: Uri): Intent {
        return Intent(Intent.ACTION_VIEW, link)
    }

    override fun openEmail(mail: EmailData): Intent {
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.email))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mail.massageTheme)
        shareIntent.putExtra(Intent.EXTRA_TEXT, mail.message)
        return shareIntent
    }
}