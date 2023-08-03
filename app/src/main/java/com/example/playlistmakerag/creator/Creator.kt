package com.example.playlistmakerag.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.search.data.Retrofit
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.settings.data.SwitchTheme
import com.example.playlistmakerag.sharing.data.ExternalNavigator

object Creator {
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }

    fun provideContext(): Context {
        return application.applicationContext
    }

    fun getPlayer(url: String): Player {
        return Player(url)
    }

    fun getRetrofit(): Retrofit {
        return Retrofit()
    }

    fun getSwitchTheme(): SwitchTheme {
        return SwitchTheme()
    }

    fun getHistory(): SearchHistory {
        return SearchHistory()
    }

    fun getNavigator(): ExternalNavigator {
        return ExternalNavigator()
    }

}