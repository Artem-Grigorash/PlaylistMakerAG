package com.example.playlistmakerag.mediateka.domain.db

import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun addPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    fun historyPlaylists(): Flow<List<Playlist>>
    suspend fun updatePlaylist(playlist: Playlist)

}