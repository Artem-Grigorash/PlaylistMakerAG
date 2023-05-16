package com.example.playlistmakerag.creator

import com.example.playlistmakerag.data.player.Player
import com.example.playlistmakerag.domain.TrackInteractor
import com.example.playlistmakerag.presentation.track.TrackPresenter

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