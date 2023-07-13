package com.example.playlistmakerag.search.domain

import android.view.View
import androidx.lifecycle.ViewModel

import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.impl.SearchInteractor
import retrofit2.Response


class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val state = MutableLiveData<SearchState>()
    fun getSearchState() : LiveData<SearchState> = state

    fun loading(){
        state.value = SearchState.Loading
    }
    fun nothingFound(){
        state.value = SearchState.NothingFound
    }
    fun badConnection(){
        state.value = SearchState.BadConnection
    }
    fun data(){
        state.value = SearchState.Data
    }

    fun makeRequest(text: String): Response<TrackResponse>?{
        return interactor.makeRequest(text)
    }

    //functions:

    fun addTrack(track: Track, place : ArrayList<Track>){
        if (place.size == 10)
            place.removeAt(9)
        if (place.contains(track))
            place.remove(track)
        place.add(0, track)
    }


    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}