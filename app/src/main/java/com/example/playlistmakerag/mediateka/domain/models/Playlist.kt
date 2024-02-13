package com.example.playlistmakerag.mediateka.domain.models

data class Playlist (
    val playlistId: String,
    val playlistName: String,
    val playlistDescription: String?,
    val imageUri: String?,
    val trackAmount: Int?,
    val addedTracks: String
)