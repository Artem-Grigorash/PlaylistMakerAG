package com.example.playlistmakerag.player.domain

interface TrackInteractor {
    fun playbackControl()

    fun getPosition(): Int

    fun delete()

}