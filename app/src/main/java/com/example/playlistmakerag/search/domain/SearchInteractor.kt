package com.example.playlistmakerag.search.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.player.domain.models.Track
import retrofit2.Response

interface SearchInteractor {
    fun makeRequest(expression: String, consumer: Consumer)
    fun read(): ArrayList<Track>
    fun write(tracks: ArrayList<Track>)
    fun getResponseState(): Boolean
    interface Consumer {
        fun consume(response: ArrayList<Track>)
    }
}