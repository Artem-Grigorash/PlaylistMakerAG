package com.example.playlistmakerag.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES

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
}