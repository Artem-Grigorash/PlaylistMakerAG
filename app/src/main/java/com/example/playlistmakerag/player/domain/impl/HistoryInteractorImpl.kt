package com.example.playlistmakerag.player.domain.impl

import com.example.playlistmakerag.player.data.db.entity.TrackEntity
import com.example.playlistmakerag.player.domain.db.HistoryInteractor
import com.example.playlistmakerag.player.domain.db.HistoryRepository
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(private val historyRepository: HistoryRepository) : HistoryInteractor {

    override fun addTrack(track: TrackEntity) {
        historyRepository.addTrack(track)
    }

    override fun deleteTrack(track: TrackEntity) {
        historyRepository.deleteTrack(track)
    }

    override fun historyTracks(): Flow<List<Track>> {
        return historyRepository.historyTracks()
    }
}