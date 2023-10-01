package com.example.playlistmakerag.player.data.db

import com.example.playlistmakerag.player.data.converters.TrackDbConvertor
import com.example.playlistmakerag.player.data.db.entity.TrackEntity
import com.example.playlistmakerag.player.domain.db.HistoryRepository
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor,
) : HistoryRepository {

    override suspend fun addTrack(track: Track) {
            appDatabase.trackDao().insertTrackEntity(trackDbConvertor.map(track))
    }

    override suspend fun deleteTrack(track: Track) {
        appDatabase.trackDao().deleteTrackEntity(trackDbConvertor.map(track))
    }

    override fun historyTracks(): Flow<List<Track>> = flow {
        val tracks = appDatabase.trackDao().getTracks()
        emit(convertFromTrackEntity(tracks))
    }


    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }

//    private fun convertFromTrackEntitySingle (track: TrackEntity): Track{
//        return trackDbConvertor.map(track)
//    }
}