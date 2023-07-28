package com.example.playlistmakerag.settings.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.app.DARK_THEME_KEY
import com.example.playlistmakerag.app.PREFERENCES

class SwitchTheme {
    fun updateThemeSetting(checked: Boolean, applicationContext: Context, sharedPref: SharedPreferences){
        (applicationContext as App).switchTheme(checked)
        sharedPref.edit()
            .putBoolean(DARK_THEME_KEY, checked)
            .apply()
    }
    fun provideSharedPreferences(context: Context) : SharedPreferences{
        return context.getSharedPreferences(PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }
}