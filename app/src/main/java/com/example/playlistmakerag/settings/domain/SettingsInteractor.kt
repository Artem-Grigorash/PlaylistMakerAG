package com.example.playlistmakerag.settings.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES

interface SettingsInteractor {
    fun updateThemeSetting(checked: Boolean, applicationContext: Context, sharedPref: SharedPreferences)
    fun provideSharedPreferences(context: Context) : SharedPreferences
}