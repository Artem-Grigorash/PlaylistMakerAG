package com.example.playlistmakerag.player.domain

sealed class TrackState{
    object Play : TrackState()
    object Pause : TrackState()
}
