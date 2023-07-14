package com.example.playlistmakerag.search.ui.view_models

sealed class SearchState {
    object Loading : SearchState()
    object Data : SearchState()
    object NothingFound : SearchState()
    object BadConnection : SearchState()
}
