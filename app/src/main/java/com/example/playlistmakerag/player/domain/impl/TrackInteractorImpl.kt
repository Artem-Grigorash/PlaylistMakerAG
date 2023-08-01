package com.example.playlistmakerag.player.domain.impl

import com.example.playlistmakerag.player.domain.PlayerInterface
import com.example.playlistmakerag.player.domain.TrackInteractor

class TrackInteractorImpl (
    private val player: PlayerInterface
) : TrackInteractor{
    override fun playbackControl() {
        player.playbackControl()
    }

    override fun getPosition(): Int {
        return player.getPosition()
    }

    override fun delete() {
        player.delete()
    }
}