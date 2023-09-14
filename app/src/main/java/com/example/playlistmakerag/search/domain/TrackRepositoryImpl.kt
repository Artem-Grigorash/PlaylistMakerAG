package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.SearchInterface
import com.example.playlistmakerag.search.data.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(private val networkClient: SearchInterface) : TrackRepository {
    override fun makeRequest(expression: String): Flow<ArrayList<Track>?> = flow {
        val response = networkClient.makeRequest(expression)
        emit(response)
    }
}
