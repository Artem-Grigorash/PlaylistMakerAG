package com.example.playlistmakerag.search.ui.view_models

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.domain.SearchInteractor
import com.example.playlistmakerag.search.domain.impl.SearchInteractorImpl
import com.example.playlistmakerag.search.ui.SearchActivity
import retrofit2.Response


class SearchViewModel(private val interactor: SearchInteractorImpl) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        fun getSearchViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App).provideSearchViewModel()
                SearchViewModel(
                    interactor
                )
            }
        }
    }

    private var latestSearchText: String? = null

    private val handler = Handler(Looper.getMainLooper())

    private var isClickAllowed = true

    fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    fun searchDebounce(text: String) {
        val searchRunnable = Runnable() {
            loading()
            makeRequest(text)
        }
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
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

    fun provideSharedPreferences(context: Context) : SharedPreferences{
        return interactor.provideSharedPreferences(context)
    }

    fun makeRequest(text: String){
        interactor.makeRequest(text, object: SearchInteractor.Consumer {
            override fun consume(response: Response<TrackResponse> ) {
                res.postValue(response)
            }
        })
    }

    fun onReloadClicked(text: String){
        loading()
        makeRequest(text)
    }

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