package com.example.playlistmakerag.settings.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.app.DARK_THEME_KEY
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.creator.Creator.provideContext
import com.example.playlistmakerag.creator.Creator.provideSharedPreferences

class SwitchTheme {

    val sharedPref = provideSharedPreferences()
    val context = provideContext()

    fun updateThemeSetting(
        checked: Boolean,
    ) {
        (context as App).switchTheme(checked)
        sharedPref.edit()
            .putBoolean(DARK_THEME_KEY, checked)
            .apply()
    }
    fun getChecked() = sharedPref.getBoolean(DARK_THEME_KEY, false)
}