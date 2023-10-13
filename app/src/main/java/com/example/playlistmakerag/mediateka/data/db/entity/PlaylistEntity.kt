package com.example.playlistmakerag.mediateka.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistName: String,
    val playlistDescription: String,
    val imageUri: String,
    val trackAmount: Int,
    val addedTracks: ArrayList<String>
)
