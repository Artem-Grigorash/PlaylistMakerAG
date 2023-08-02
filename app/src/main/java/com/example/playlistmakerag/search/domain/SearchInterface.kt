package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.TrackResponse
import retrofit2.Response
import java.util.ArrayList

interface SearchInterface {
    fun makeRequest(text: String): ArrayList<Track>?
}