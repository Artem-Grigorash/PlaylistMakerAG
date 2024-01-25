package com.example.playlistmakerag.mediateka.domain.impl

import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.db.PlaylistRepository
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl (private val playlistRepository: PlaylistRepository) :
    PlaylistInteractor {
    override suspend fun addPlaylist(playlist: Playlist) {
        playlistRepository.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistRepository.deletePlaylist(playlist)
    }

    override fun historyPlaylists(): Flow<List<Playlist>> {
        return playlistRepository.historyPlaylists()
    }

    override suspend fun updatePlaylist(newPlaylist: Playlist) {
        playlistRepository.updatePlaylist(newPlaylist)
    }

}