package com.example.playlistmakerag.search.data

import com.example.playlistmakerag.player.domain.models.Track
import java.util.ArrayList

interface SearchInterface {
    suspend fun makeRequest(text: String): ArrayList<Track>?
}