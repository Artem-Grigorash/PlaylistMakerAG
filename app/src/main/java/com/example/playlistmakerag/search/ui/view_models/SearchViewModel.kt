package com.example.playlistmakerag.search.ui.view_models

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInteractor



class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        fun getSearchViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSearchViewModel()
                SearchViewModel(
                    interactor
                )
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    private var isClickAllowed = true

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun searchDebounce(text: String) {
        val searchRunnable = Runnable {
            makeRequest(text)
        }
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private val state = MutableLiveData<SearchState>()
    fun getSearchState(): LiveData<SearchState> = state

    private val res = MutableLiveData<TrackResponse>()
    fun getSearchStateResponse(): LiveData<TrackResponse> = res

    private val history = MutableLiveData<ArrayList<Track>>()
    fun getHistory(): LiveData<ArrayList<Track>> = history

    fun reloadTracks(){
        history.value = interactor.read()
    }


    fun searchTracks(response: TrackResponse, text: String, tracks: ArrayList<Track>) {
        if (text.isNotEmpty()) {
            if (response.code == 200) {
                if (response.results?.isNotEmpty() == true) {
                    data()
                }
                if (tracks.isEmpty()) {
                    nothingFound()
                }
            } else {
                badConnection()
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
        interactor.makeRequest(text, object : SearchInteractor.Consumer {
            override fun consume(response: TrackResponse) {
                res.postValue(response)
            }
        })
    }

    fun onReloadClicked(text: String) {
        loading()
        makeRequest(text)
    }

    private fun addTrack(track: Track, place: ArrayList<Track>) {
        if (place.size == 10)
            place.removeAt(9)
        if (place.contains(track))
            place.remove(track)
        place.add(0, track)
    }

    fun clean() {
        val recentSongs: ArrayList<Track> = interactor.read()
        recentSongs.clear()
        interactor.write(recentSongs)
    }


    fun onItemClicked(track: Track) {
        val recentSongs: ArrayList<Track> = interactor.read()
        addTrack(track, recentSongs)
        interactor.write(recentSongs)

    }

    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}