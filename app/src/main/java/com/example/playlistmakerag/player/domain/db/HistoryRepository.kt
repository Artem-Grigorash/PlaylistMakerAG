package com.example.playlistmakerag.player.domain.db

import com.example.playlistmakerag.player.data.db.entity.TrackEntity
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun addTrack(track: Track)

    fun deleteTrack(track: Track)

    fun historyTracks(): Flow<List<Track>>

}