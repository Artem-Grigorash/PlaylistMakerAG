package com.example.playlistmakerag.domain

import android.widget.TextView

interface PlayerInterface {
    fun preparePlayer()
    fun playbackControl()
    fun startTimer(progress : TextView)
}