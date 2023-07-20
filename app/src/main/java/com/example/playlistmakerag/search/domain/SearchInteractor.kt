package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.data.dto.TrackResponse
import retrofit2.Response

interface SearchInteractor {
    fun makeRequest(expression: String, consumer: Consumer)

    interface Consumer {
        fun consume(response: Response<TrackResponse>)
    }
}