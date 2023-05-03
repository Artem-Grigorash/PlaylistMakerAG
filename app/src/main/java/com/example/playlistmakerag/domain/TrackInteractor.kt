package com.example.playlistmakerag.domain

import android.widget.TextView

class TrackInteractor(
    private val player: PlayerInterface
) {
    fun preparePlayer(){
        player.preparePlayer()
    }
    fun playbackControl(){
        player.playbackControl()
    }
    fun startTimer(progress : TextView){
        player.startTimer(progress)
    }
}