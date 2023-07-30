package com.example.playlistmakerag.player.domain.impl

import com.example.playlistmakerag.player.domain.PlayerInterface

class TrackInteractor(
    private val player: PlayerInterface
) {
    fun playbackControl() {
        player.playbackControl()
    }

    fun getPosition(): Int {
        return player.getPosition()
    }

    fun delete() {
        player.delete()
    }
}