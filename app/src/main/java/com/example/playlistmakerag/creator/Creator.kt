package com.example.playlistmakerag.creator

import com.example.playlistmakerag.data.player.Player
import com.example.playlistmakerag.domain.TrackInteractor
import com.example.playlistmakerag.presentation.track.TrackPresenter

object Creator {
    private fun getPlayer(): Player {
        return Player()
    }

    fun providePresenter(): TrackPresenter {
        return TrackPresenter(
            interactor = TrackInteractor(getPlayer())
        )
    }
}