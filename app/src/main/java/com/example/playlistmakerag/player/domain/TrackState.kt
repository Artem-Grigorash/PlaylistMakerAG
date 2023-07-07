package com.example.playlistmakerag.player.domain

import com.example.playlistmakerag.player.domain.models.Track

sealed class TrackState{
    object Play : TrackState()
    object Pause : TrackState()
}
