package com.example.playlistmakerag.player.data.dto

import com.example.playlistmakerag.player.domain.models.Track

data class TrackResponse(
    val code : Int,
    val results: ArrayList<Track>?
)