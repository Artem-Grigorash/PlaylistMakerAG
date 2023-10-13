package com.example.playlistmakerag.mediateka.domain.models

data class Playlist (
    val playlistName: String,
    val playlistDescription: String,
    val imageUri: String,
    val trackAmount: Int,
    val addedTracks: ArrayList<String>
)