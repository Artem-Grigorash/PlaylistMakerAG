package com.example.playlistmakerag.mediateka.domain.models

import com.example.playlistmakerag.player.domain.models.Track

data class Playlist (
    val playlistName: String,
    val playlistDescription: String?,
    val imageUri: String?,
    val trackAmount: Int,
    val addedTracks: String
)