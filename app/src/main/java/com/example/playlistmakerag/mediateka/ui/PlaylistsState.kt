package com.example.playlistmakerag.mediateka.ui

import com.example.playlistmakerag.mediateka.domain.models.Playlist

sealed interface PlaylistsState {

    object Loading : PlaylistsState

    data class Content(
        val playlists: List<Playlist>
    ) : PlaylistsState

    data class Empty(
        val message: String
    ) : PlaylistsState
}