package com.example.playlistmakerag.search.ui.view_models

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.domain.SearchInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var searchJob: Job? = null

    private var isClickAllowed = true

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    fun searchDebounce(text: String) {

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            makeRequest(text)
        }
    }

    private val state = MutableLiveData<SearchState>()
    fun getSearchState(): LiveData<SearchState> = state

    private val res = MutableLiveData<ArrayList<Track>>()
    fun getSearchStateResponse(): LiveData<ArrayList<Track>> = res

    private val history = MutableLiveData<ArrayList<Track>>()
    fun getHistory(): LiveData<ArrayList<Track>> = history

    fun reloadTracks() {
        viewModelScope.launch {
            history.value = interactor.read()
        }
    }


    fun searchTracks(response: ArrayList<Track>, text: String) {
        if (text.isNotEmpty()) {
            if (interactor.getResponseState()) {
                badConnection()
            } else {
                if (response.isNotEmpty()) {
                    data()
                } else {
                    nothingFound()
                }
            }
        }
    }


    private fun loading() {
        state.value = SearchState.Loading
    }

    private fun nothingFound() {
        state.value = SearchState.NothingFound
    }

    private fun badConnection() {
        state.value = SearchState.BadConnection
    }

    fun data() {
        state.value = SearchState.Data
    }

    private fun makeRequest(text: String) {
        viewModelScope.launch{
            interactor
                .makeRequest(text)
                .collect{response ->
                    res.postValue(response)
                }
        }
    }

    fun onReloadClicked(text: String) {
        loading()
        makeRequest(text)
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

    fun clean() {
        viewModelScope.launch {
            val recentSongs: ArrayList<Track> = interactor.read()
            recentSongs.clear()
            interactor.write(recentSongs)
        }
    }


    fun onItemClicked(track: Track) {
        viewModelScope.launch {
            val recentSongs: ArrayList<Track> = interactor.read()
            addTrack(track, recentSongs)
            interactor.write(recentSongs)
        }

    }

    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}