package com.example.playlistmakerag.sharing.domain

import android.content.Intent

interface SharingInteractor {
    fun shareApp(link: String): Intent
    fun openTerms(link: String): Intent
    fun openSupport(message: String, massageTheme: String): Intent
}