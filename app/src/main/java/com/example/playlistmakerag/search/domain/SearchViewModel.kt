package com.example.playlistmakerag.search.domain

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.search.data.HISTORY_KEY
import com.example.playlistmakerag.PRFERENCES
import com.example.playlistmakerag.R
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayActivity
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.player.domain.TrackState
import com.example.playlistmakerag.search.domain.SearchViewModel
import com.example.playlistmakerag.search.ui.TracksAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }


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


    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { searchTracks() }

    private var isClickAllowed = true


    //    private val tracks = ArrayList<Track>()
    private val recentTracks = ArrayList<Track>()


    private lateinit var viewModel: SearchViewModel

    //functions:
    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    //data
    private fun addTrack(track: Track, place : ArrayList<Track>){
        if (place.size == 10)
            place.removeAt(9)
        if (place.contains(track))
            place.remove(track)
        place.add(0, track)
    }


    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }



//    private fun showMessage(text: String, additionalMessage: String, holderImage: Int) {
//        if (text.isNotEmpty()) {
////            placeholderMessage.visibility = View.VISIBLE
////            placeholder.visibility = View.VISIBLE
////            tracks.clear()
////            adapter.notifyDataSetChanged()
////            placeholderMessage.text = text
////            placeholder.setImageResource(holderImage)
//            if (additionalMessage.isNotEmpty()) {
//                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
//                    .show()
//            }
//        } else {
////            placeholderMessage.visibility = View.GONE
////            placeholder.visibility = View.GONE
//        }
//    }


    private fun searchTracks(){
//        reloadButton.visibility = View.GONE
//        placeholder.visibility = View.GONE
//        placeholderMessage.visibility = View.GONE
//        recyclerView.visibility = View.GONE
//        progressBar.visibility = View.VISIBLE
//        reloadButton.isClickable = false
//        if (inputEditText.text.isNotEmpty()) {
//            trackService.search(inputEditText.text.toString()).enqueue(object :
//                Callback<TrackResponse> {
//                override fun onResponse(call: Call<TrackResponse>,
//                                        response: Response<TrackResponse>
//                ) {
////                    progressBar.visibility = View.GONE
//                    if (response.code() == 200) {
//                        tracks.clear()
//                        if (response.body()?.results?.isNotEmpty() == true) {
//                            tracks.addAll(response.body()?.results!!)
//                            adapter.notifyDataSetChanged()
////                            recyclerView.visibility = View.VISIBLE
//                        }
//                        if (tracks.isEmpty()) {
//                            showMessage(getString(R.string.nothing_found), "",
//                                R.drawable.tracks_placeholder_nf
//                            )
//                        }
//                    }
//
//                    else {
//                        showMessage(getString(R.string.something_went_wrong), response.code().toString(),
//                            R.drawable.tracks_placeholder_ce
//                        )
////                        reloadButton.visibility = View.VISIBLE
////                        reloadButton.isClickable = true
//                    }
//                }
//
//                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
//                    showMessage(getString(R.string.something_went_wrong), t.message.toString(),
//                        R.drawable.tracks_placeholder_ce
//                    )
////                    reloadButton.visibility = View.VISIBLE
////                    progressBar.visibility = View.GONE
////                    reloadButton.isClickable = true
//                }
//
//            })
//        }
//        else{
////            progressBar.visibility = View.GONE
//        }
//    }


    }
}