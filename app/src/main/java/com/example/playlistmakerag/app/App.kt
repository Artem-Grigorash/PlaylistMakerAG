package com.example.playlistmakerag.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakerag.player.di.dataModulePlayer
import com.example.playlistmakerag.player.di.interactorModulePlayer
import com.example.playlistmakerag.player.di.viewModelModulePlayer
import com.example.playlistmakerag.search.di.dataModuleSearch
import com.example.playlistmakerag.search.di.interactorModuleSearch
import com.example.playlistmakerag.search.di.viewModelModuleSearch
import com.example.playlistmakerag.settings.di.dataModuleSettings
import com.example.playlistmakerag.settings.di.interactorModuleSettings
import com.example.playlistmakerag.settings.di.viewModelModuleSettings
import com.example.playlistmakerag.sharing.di.dataModuleSharing
import com.example.playlistmakerag.sharing.di.interactorModuleSharing
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


const val PREFERENCES = "preferences"
const val DARK_THEME_KEY = "key_for_dark_theme"


class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        val sharedPref = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            if (sharedPref.getBoolean(DARK_THEME_KEY, false)) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )

        startKoin {
            androidContext(this@App)
            modules(dataModulePlayer, dataModuleSearch, dataModuleSettings, dataModuleSharing,
                interactorModuleSharing, interactorModuleSearch, interactorModuleSettings, interactorModulePlayer,
                viewModelModuleSearch, viewModelModuleSettings, viewModelModulePlayer)
        }

    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}