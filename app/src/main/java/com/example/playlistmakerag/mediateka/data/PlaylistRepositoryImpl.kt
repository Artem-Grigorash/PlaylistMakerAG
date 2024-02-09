package com.example.playlistmakerag.mediateka.data

import com.example.playlistmakerag.mediateka.data.converters.PlaylistDbConverter
import com.example.playlistmakerag.mediateka.data.db.entity.PlaylistEntity
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.domain.db.PlaylistRepository
import com.example.playlistmakerag.player.data.converters.TrackDbConvertor
import com.example.playlistmakerag.player.data.db.AppDatabase
import com.example.playlistmakerag.player.data.db.entity.TrackEntity
import com.example.playlistmakerag.player.domain.db.HistoryRepository
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val playlistDatabase: AppDatabase,
    private val playlistDbConvertor: PlaylistDbConverter,
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: Playlist) {
        playlistDatabase.playlistDao().insertPlaylistEntity(playlistDbConvertor.map(playlist))
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDatabase.playlistDao().deletePlaylistEntity(playlistDbConvertor.map(playlist))
    }

    override fun historyPlaylists(): Flow<List<Playlist>> = flow {
        val playlists = playlistDatabase.playlistDao().getPlaylists()
        emit(convertFromPlaylistEntity(playlists))
    }

    private fun convertFromPlaylistEntity(playlist: List<PlaylistEntity>): List<Playlist> {
        return playlist.map { playlist -> playlistDbConvertor.map(playlist) }
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDatabase.playlistDao().updatePlaylist(playlistDbConvertor.map(playlist))
    }

    override suspend fun getPlaylist(playlistId: String){
        playlistDatabase.playlistDao().getPlaylist(playlistId)
    }
}