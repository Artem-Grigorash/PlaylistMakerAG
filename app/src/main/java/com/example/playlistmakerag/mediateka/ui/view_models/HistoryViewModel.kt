package com.example.playlistmakerag.mediateka.ui.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.ui.history.HistoryState
import com.example.playlistmakerag.player.domain.db.HistoryInteractor
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val context: Context,
    private val historyInteractor: HistoryInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<HistoryState>()

    fun observeState(): LiveData<HistoryState> = stateLiveData

    fun fillData() {
        renderState(HistoryState.Loading)
        viewModelScope.launch {
            historyInteractor
                .historyTracks()
                .collect { movies ->
                    processResult(movies)
                }
        }
    }

    private fun processResult(movies: List<Track>) {
        if (movies.isEmpty()) {
            renderState(HistoryState.Empty(context.getString(R.string.nothing_searched_yet)))
        } else {
            renderState(HistoryState.Content(movies))
        }
    }

    private fun renderState(state: HistoryState) {
        stateLiveData.postValue(state)
    }


    private var isClickAllowed = true

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(1000L)
                isClickAllowed = true
            }
        }
        return current
    }
}