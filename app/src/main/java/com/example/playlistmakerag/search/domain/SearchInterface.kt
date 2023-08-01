package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.search.data.dto.TrackResponse
import retrofit2.Response

interface SearchInterface {
    fun makeRequest(text: String): TrackResponse?
}