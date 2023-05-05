package com.example.playlistmakerag.creator

import com.example.playlistmakerag.domain.TrackInteractor
import com.example.playlistmakerag.presentation.track.TrackPresenter
import com.example.playlistmakerag.presentation.track.TrackView

object Creator {

    fun providePresenter(view: TrackView): TrackPresenter {
        return TrackPresenter(
            interactor =
        )
    }
}