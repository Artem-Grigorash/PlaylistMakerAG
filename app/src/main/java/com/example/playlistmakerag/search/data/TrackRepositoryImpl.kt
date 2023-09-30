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
        val response = networkClient.makeRequest(expression)
        with(response as TrackResponse) {
            val data = results.map {
                Track(it.id, it.resultType, it.image, it.title, it.description)
            }
            //сохраняем список фильмов в базу данных
            saveTrack(results)
            emit(response)
        }
    }
    private suspend fun saveTrack(track: TrackDao) {
        val movieEntities =  movieDbConvertor.map(track)
        appDatabase.trackDao().insertTrackEntity(movieEntities)
    }
}
