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
import com.example.playlistmakerag.search.ui.view_models.SearchViewModel
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

    fun addTrack(track: Track, place: ArrayList<Track>) {
        if (place.size == 10)
            place.removeAt(9)
        val ids = ArrayList<String>()
        for (element in place)
            ids.add(element.trackId)
        if (ids.contains(track.trackId))
            place.remove(track)
        place.add(0, track)
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