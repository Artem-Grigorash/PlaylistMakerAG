package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track

interface SearchInteractor {
    fun makeRequest(expression: String, consumer: Consumer)
    fun read(): ArrayList<Track>
    fun write(tracks: ArrayList<Track>)
    fun getResponseState(): Boolean
    interface Consumer {
        fun consume(response: ArrayList<Track>)
    }
}