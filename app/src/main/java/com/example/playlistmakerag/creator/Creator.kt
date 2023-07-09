package com.example.playlistmakerag.creator

import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel

object Creator {
    private fun getPlayer(url:String): Player {
        return Player(url)
    }

    fun provideViewModel(url:String): TrackViewModel {
        return TrackViewModel(
            interactor = TrackInteractor(getPlayer(url))
        )
    }
}