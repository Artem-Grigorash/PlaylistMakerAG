package com.example.playlistmakerag.mediateka.domain.db

import com.example.playlistmakerag.mediateka.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun addPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    fun historyPlaylists(): Flow<List<Playlist>>
    suspend fun updatePlaylist(newPlaylist: Playlist)
}