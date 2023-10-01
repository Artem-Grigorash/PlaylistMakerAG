package com.example.playlistmakerag.search.data

import com.example.playlistmakerag.player.data.converters.TrackDbConvertor
import com.example.playlistmakerag.player.data.db.AppDatabase
import com.example.playlistmakerag.player.data.db.dao.TrackDao
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(
    private val networkClient: SearchInterface,
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: TrackDbConvertor
) : TrackRepository {
    override fun makeRequest(expression: String): Flow<ArrayList<Track>?> = flow {

        val favorite = appDatabase.trackDao().getTracks().map { track -> movieDbConvertor.map(track) }
        val response = networkClient.makeRequest(expression)
        response?.map { track: Track ->
            if (favorite.contains(track))
                track.isFavorite=true
        }
        emit(response)
    }
}


