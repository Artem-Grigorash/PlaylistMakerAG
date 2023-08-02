package com.example.playlistmakerag.player.ui.view_models

sealed class TrackState {
    object Play : TrackState()
    object Pause : TrackState()
}
