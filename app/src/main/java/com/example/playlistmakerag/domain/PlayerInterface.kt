package com.example.playlistmakerag.domain


interface PlayerInterface {
    fun playbackControl()
    fun getPosition() : Int
    fun delete()
}