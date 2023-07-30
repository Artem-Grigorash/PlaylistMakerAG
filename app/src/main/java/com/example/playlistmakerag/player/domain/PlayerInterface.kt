package com.example.playlistmakerag.player.domain


interface PlayerInterface {
    fun playbackControl()
    fun getPosition(): Int
    fun delete()
}