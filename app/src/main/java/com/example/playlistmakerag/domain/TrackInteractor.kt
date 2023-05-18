package com.example.playlistmakerag.domain

class TrackInteractor(
    private val player: PlayerInterface
) {
    fun playbackControl(){
        player.playbackControl()
    }
    fun getPosition():Int{
        return player.getPosition()
    }
    fun delete(){
        player.delete()
    }
}