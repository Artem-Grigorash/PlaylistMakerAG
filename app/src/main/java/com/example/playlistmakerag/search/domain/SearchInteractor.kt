package com.example.playlistmakerag.search.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.player.domain.models.Track
import retrofit2.Response

interface SearchInteractor {
    fun makeRequest(expression: String, consumer: Consumer)
    fun read(pref: SharedPreferences): ArrayList<Track>

    fun provideSharedPreferences(context: Context): SharedPreferences

    fun write(pref: SharedPreferences, tracks: ArrayList<Track>)
    interface Consumer {
        fun consume(response: TrackResponse)
    }
}