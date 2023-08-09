package com.example.playlistmakerag.search.domain

import com.example.playlistmakerag.player.domain.models.Track

interface HistoryInterface {
    fun read(): ArrayList<Track>
    fun write(tracks: ArrayList<Track>)
}