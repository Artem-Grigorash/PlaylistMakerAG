package com.example.playlistmakerag.settings.domain

import android.content.Context
import android.content.SharedPreferences

interface SettingsInteractor {
    fun updateThemeSetting(
        checked: Boolean,
        applicationContext: Context,
        sharedPref: SharedPreferences
    )

    fun provideSharedPreferences(context: Context): SharedPreferences

    fun getChecked(sharedPref: SharedPreferences) : Boolean
}