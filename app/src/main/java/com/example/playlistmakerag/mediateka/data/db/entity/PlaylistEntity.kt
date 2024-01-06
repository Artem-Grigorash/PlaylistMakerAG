package com.example.playlistmakerag.mediateka.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey
    val playlistName: String,
    val playlistDescription: String?,
    val imageUri: String?,
    val trackAmount: Int?,
    val addedTracks: String
)
