package com.example.playlistmakerag.creator

import android.app.Application

object Creator {
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }
}