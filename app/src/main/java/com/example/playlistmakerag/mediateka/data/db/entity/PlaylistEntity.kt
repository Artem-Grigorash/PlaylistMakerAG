package com.example.playlistmakerag.mediateka.data.db.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistName: String,
    val playlistDescription: String?,
    val imageUri: Uri?,
    val trackAmount: Int,
    val addedTracks: ArrayList<String>
)
