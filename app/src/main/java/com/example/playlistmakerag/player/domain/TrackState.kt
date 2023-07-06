package com.example.playlistmakerag.player.domain

import com.example.playlistmakerag.player.domain.models.Track

sealed class TrackState{
    object Loading: TrackState()
    data class Content(
        val trackModel: Track,
    ): TrackState()
}
