package com.example.playlistmakerag.mediateka.ui.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.ui.PlaylistsState
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val context: Context,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<PlaylistsState>()

    fun observeState(): LiveData<PlaylistsState> = stateLiveData

    fun fillData() {
        renderState(PlaylistsState.Loading)
        viewModelScope.launch {
            playlistInteractor
                .historyPlaylists()
                .collect { playlist ->
                    processResult(playlist)
                }
        }
    }

    private fun processResult(movies: List<Playlist>) {
        if (movies.isEmpty()) {
            renderState(PlaylistsState.Empty(context.getString(R.string.nothing_searched_yet)))
        } else {
            renderState(PlaylistsState.Content(movies))
        }
    }

    private fun renderState(state: PlaylistsState) {
        stateLiveData.postValue(state)
    }
}