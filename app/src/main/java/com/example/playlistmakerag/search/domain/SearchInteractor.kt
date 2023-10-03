package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun read(): ArrayList<Track>
    fun write(tracks: ArrayList<Track>)
    fun getResponseState(): Boolean

    fun makeRequest(expression: String) : Flow<ArrayList<Track>?>
}