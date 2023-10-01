package com.example.playlistmakerag.mediateka.ui.history

import com.example.playlistmakerag.player.domain.models.Track

sealed interface HistoryState {

    object Loading : HistoryState

    data class Content(
        val movies: List<Track>
    ) : HistoryState

    data class Empty(
        val message: String
    ) : HistoryState
}