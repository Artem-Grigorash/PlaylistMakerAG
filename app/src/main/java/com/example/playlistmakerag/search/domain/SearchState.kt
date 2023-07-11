package com.example.playlistmakerag.search.domain

sealed class SearchState {
    object Loading : SearchState()
    object Data : SearchState()
    object NothingFound : SearchState()
    object BadConnection : SearchState()
}
