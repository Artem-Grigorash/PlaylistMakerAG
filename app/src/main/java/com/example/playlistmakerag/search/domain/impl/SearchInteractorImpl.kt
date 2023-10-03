package com.example.playlistmakerag.search.domain.impl

import com.example.playlistmakerag.player.data.converters.TrackDbConvertor
import com.example.playlistmakerag.player.data.db.AppDatabase
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.domain.HistoryInterface
import com.example.playlistmakerag.search.domain.SearchInteractor
import com.example.playlistmakerag.search.domain.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInteractorImpl(
    private val search: TrackRepository,
    private val history: HistoryInterface,
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor) :
    SearchInteractor {

    var responseIsEmpty = false

    override fun getResponseState(): Boolean {
        return responseIsEmpty
    }

    override fun makeRequest(expression: String): Flow<ArrayList<Track>> {
            return search.makeRequest(expression).map { result ->
                if (result != null) {
                    responseIsEmpty = false
                    result
                } else {
                    responseIsEmpty = true
                    ArrayList()
                }
            }

    }



    override suspend fun read(): ArrayList<Track> {
        val tracks = history.read()
        for (track in tracks) {
            val favorite = appDatabase.trackDao().getTracks().map { track -> trackDbConvertor.map(track) }
            if (favorite.contains(track))
                track.isFavorite = true
        }
        return tracks
    }

    override fun write(tracks: ArrayList<Track>) {
        history.write(tracks)
    }

}