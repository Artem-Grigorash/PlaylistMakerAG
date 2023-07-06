package com.example.playlistmakerag.player.domain

interface TrackView {
    fun render(state: TrackState)
}