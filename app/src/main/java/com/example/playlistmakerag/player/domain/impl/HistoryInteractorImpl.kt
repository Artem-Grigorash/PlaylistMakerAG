package com.example.playlistmakerag.player.domain.impl

import com.example.playlistmakerag.player.domain.db.HistoryInteractor
import com.example.playlistmakerag.player.domain.db.HistoryRepository
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(private val historyRepository: HistoryRepository) : HistoryInteractor {

    override suspend fun addTrack(track: Track) {
        historyRepository.addTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        historyRepository.deleteTrack(track)
    }

    override fun historyTracks(): Flow<List<Track>> {
        return historyRepository.historyTracks()
    }
}