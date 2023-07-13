package com.example.playlistmakerag.search.domain.impl

import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInterface
import retrofit2.Response

class SearchInteractor (private val search : SearchInterface) {
    fun makeRequest(text: String) : Response<TrackResponse>?{
        return search.makeRequest(text)
    }
}