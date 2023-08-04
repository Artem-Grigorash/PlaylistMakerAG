package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track
import java.util.ArrayList

interface SearchInterface {
    fun makeRequest(text: String): ArrayList<Track>?
}