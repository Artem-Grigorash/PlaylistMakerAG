package com.example.playlistmakerag.player.ui

import com.example.playlistmakerag.player.ui.view_models.TrackState

interface TrackView {
    fun render(state: TrackState)
}