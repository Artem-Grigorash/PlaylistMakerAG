package com.example.playlistmakerag.sharing.domain

import android.content.Context
import android.content.Intent

interface SharingInteractor {
    fun shareApp(context: Context) : Intent
    fun openTerms(context: Context) : Intent
    fun openSupport(context: Context) : Intent
}