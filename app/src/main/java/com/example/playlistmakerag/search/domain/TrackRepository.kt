package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun makeRequest(expression: String): Flow<ArrayList<Track>?>
}