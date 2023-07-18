package com.example.playlistmakerag.search.ui.view_models

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.domain.SearchInteractor
import com.example.playlistmakerag.search.domain.impl.SearchInteractorImpl
import retrofit2.Response


class SearchViewModel(private val interactor: SearchInteractorImpl) : ViewModel() {

    companion object {
        fun getSearchViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSearchViewModel()
                SearchViewModel(
                    interactor
                )
            }
        }
    }

    private val state = MutableLiveData<SearchState>()
    fun getSearchState() : LiveData<SearchState> = state

    private val res = MutableLiveData<Response<TrackResponse>>()
    fun getSearchStateResponse() : LiveData<Response<TrackResponse>> = res

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

    fun makeRequest(text: String){
        interactor.makeRequest(text, object: SearchInteractor.Consumer {
            override fun consume(response: Response<TrackResponse> ) {
                res.postValue(response)
            }
        })
    }

    //functions:

    fun addTrack(track: Track, place : ArrayList<Track>){
        if (place.size == 10)
            place.removeAt(9)
        if (place.contains(track))
            place.remove(track)
        place.add(0, track)
    }

    fun clean(sharedPref : SharedPreferences){
        val recentSongs : ArrayList<Track> = SearchHistory().read(sharedPref)
        recentSongs.clear()
        SearchHistory().write(sharedPref,recentSongs)
    }

    fun OnItemClicked(track: Track, sharedPref : SharedPreferences){
        val recentSongs: ArrayList<Track> = SearchHistory().read(sharedPref)
        addTrack(track, recentSongs)
        SearchHistory().write(sharedPref, recentSongs)

    }

    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}