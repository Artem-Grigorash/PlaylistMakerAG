package com.example.playlistmakerag.mediateka.domain.models

import android.net.Uri

data class Playlist (
    val playlistName: String,
    val playlistDescription: String?,
    val imageUri: Uri?,
    val trackAmount: Int,
    val addedTracks: ArrayList<String>?
)