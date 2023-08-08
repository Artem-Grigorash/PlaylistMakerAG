package com.example.playlistmakerag.sharing.domain

import android.content.Intent
import android.net.Uri
import com.example.playlistmakerag.sharing.data.EmailData

interface ExternalNavigatorInterface {

    fun shareLink(link: String): Intent

    fun openLink(link: Uri): Intent

    fun openEmail(mail: EmailData): Intent
}