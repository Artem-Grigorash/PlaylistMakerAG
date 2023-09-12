package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun read(): ArrayList<Track>
    fun write(tracks: ArrayList<Track>)
    fun getResponseState(): Boolean

    fun makeRequest(expression: String) : Flow<ArrayList<Track>?>
//    interface Consumer {
//        fun consume(response: ArrayList<Track>)
//    }
}