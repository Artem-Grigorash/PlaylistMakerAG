package com.example.playlistmakerag.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import com.example.playlistmakerag.search.data.Retrofit
import com.example.playlistmakerag.search.domain.impl.SearchInteractorImpl
import com.example.playlistmakerag.settings.data.SwitchTheme
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerag.sharing.data.ExternalNavigator
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

    private fun getPlayer(url: String): Player {
        return Player(url)
    }

    fun provideTrackInteractor(url: String): TrackInteractor {
        return TrackInteractor(getPlayer(url))
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit()
    }

    fun provideSearchViewModel(): SearchInteractorImpl {
        return SearchInteractorImpl(getRetrofit())
    }

    private fun getSwitchTheme(): SwitchTheme {
        return SwitchTheme()
    }

    fun provideSettingsViewModel(): SettingsInteractorImpl {
        return SettingsInteractorImpl(getSwitchTheme())
    }

    private fun getNavigator(): ExternalNavigator {
        return ExternalNavigator()
    }

    fun provideSharingViewModel(): SharingInteractorImpl {
        return SharingInteractorImpl(getNavigator())
    }
}