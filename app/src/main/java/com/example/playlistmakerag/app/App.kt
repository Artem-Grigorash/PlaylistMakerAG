package com.example.playlistmakerag.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakerag.creator.Creator.getHistory
import com.example.playlistmakerag.creator.Creator.getNavigator
import com.example.playlistmakerag.creator.Creator.getPlayer
import com.example.playlistmakerag.creator.Creator.getRetrofit
import com.example.playlistmakerag.creator.Creator.getSwitchTheme
import com.example.playlistmakerag.player.domain.impl.TrackInteractorImpl
import com.example.playlistmakerag.search.domain.impl.SearchInteractorImpl
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerag.sharing.domain.impl.SharingInteractorImpl


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

    fun provideTrackInteractor(url: String): TrackInteractorImpl {
        return TrackInteractorImpl(getPlayer(url))
    }

    fun provideSearchViewModel(): SearchInteractorImpl {
        return SearchInteractorImpl(getRetrofit(), getHistory())
    }

    fun provideSettingsViewModel(): SettingsInteractorImpl {
        return SettingsInteractorImpl(getSwitchTheme())
    }

    fun provideSharingViewModel(): SharingInteractorImpl {
        return SharingInteractorImpl(getNavigator())
    }
}