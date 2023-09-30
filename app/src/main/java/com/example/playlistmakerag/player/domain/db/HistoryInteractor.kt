package com.example.playlistmakerag.player.domain.db

import com.example.playlistmakerag.player.data.db.entity.TrackEntity
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {

    fun addTrack(track: TrackEntity)

    fun deleteTrack(track: TrackEntity)

    fun historyTracks(): Flow<List<Track>>

}