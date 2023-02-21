package com.example.playlistmakerag

import android.app.Application


const val PRFERENCES = "preferences"
const val DARK_THEME_KEY = "key_for_dark_theme"


class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

    }
}