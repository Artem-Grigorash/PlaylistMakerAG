package com.example.playlistmakerag.creator

import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import com.example.playlistmakerag.player.domain.TrackPresenter

object Creator {
    private fun getPlayer(url:String): Player {
        return Player(url)
    }

    fun providePresenter(url:String): TrackPresenter {
        return TrackPresenter(
            interactor = TrackInteractor(getPlayer(url))
        )
    }
}