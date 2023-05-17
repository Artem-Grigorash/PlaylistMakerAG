package com.example.playlistmakerag.domain

class TrackInteractor(
    private val player: PlayerInterface
) {
//    fun preparePlayer(){
//        player.preparePlayer()
//    }
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