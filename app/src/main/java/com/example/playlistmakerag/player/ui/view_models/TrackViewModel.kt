package com.example.playlistmakerag.player.ui.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.TrackInteractor
import com.example.playlistmakerag.player.domain.db.HistoryInteractor
import com.example.playlistmakerag.player.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewModel(private val interactor: TrackInteractor, private val historyInteractor : HistoryInteractor, url: String, private val playlistInteractor: PlaylistInteractor) : ViewModel() {
    companion object {
        private const val REFRESH_MILLIS = 300L
    }
     init {
         interactor.setUrl(url)
     }

    var favorite: List<Track> = ArrayList()

    lateinit var actualTrack : Track
    fun getTrack(track: Track){
        actualTrack = track
        checkIsFavorite()
    }

    private val state = MutableLiveData<TrackState>()
    fun getTrackState(): LiveData<TrackState> = state

    private val time = MutableLiveData<String>()
    fun getTime(): LiveData<String> = time

    private val message = MutableLiveData<String>()
    fun getMessage(): LiveData<String> = message

    private var timerJob: Job? = null

    fun onPlayClicked() {
        if (state.value == TrackState.Play)
            state.value = TrackState.Pause
        else
            state.value = TrackState.Play
    }

    private val isFavorite  = MutableLiveData<Boolean>()
    fun getIsFavorite(): LiveData<Boolean> = isFavorite

    fun onLikeClicked(){
        if(isFavorite.value==true){
            isFavorite.value=false
            viewModelScope.launch {
                historyInteractor.deleteTrack(actualTrack)
            }
        }
        else{
            isFavorite.value=true
            viewModelScope.launch {
                historyInteractor.addTrack(actualTrack)
            }
        }
    }

    fun addToPlaylist(playlist: Playlist){
        if(playlist.addedTracks?.contains(actualTrack.trackId) == true)
            message.postValue("Трек уже добавлен в плейлист ${playlist.playlistName}")
        else {
            message.postValue("Добавлено в плейлист ${playlist.playlistName}")
            playlist.addedTracks
        }
    }

    private val stateLiveData = MutableLiveData<List<Playlist>>()

    fun observeState(): LiveData<List<Playlist>> = stateLiveData

    fun fillData() {
        viewModelScope.launch {
            playlistInteractor
                .historyPlaylists()
                .collect { playlist ->
                    stateLiveData.postValue(playlist)
                }
        }
    }

    private fun checkIsFavorite(){
        viewModelScope.launch {
             historyInteractor
                 .historyTracks()
                 .collect { tracks ->
                     favorite=tracks
                     isFavorite.value = tracks.map { it.trackId }.contains(actualTrack.trackId)
                 }
         }
    }



    fun delete() {
        interactor.delete()
        state.value = TrackState.Pause
    }

    fun startPlayer() {
        interactor.playbackControl()
        startTimer()
    }

    fun pausePlayer() {
        interactor.playbackControl()
        timerJob?.cancel()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (state.value == TrackState.Play) {
                val elapsedTime = interactor.getPosition()
                val duration = 29700
                val remainingTime = duration - elapsedTime

                if (remainingTime > 0) {
                    time.value = SimpleDateFormat(
                        "mm:ss",
                        Locale.getDefault()
                    ).format(interactor.getPosition())
                    delay(REFRESH_MILLIS)
                } else {
                    time.value = "00:00"
                    state.value = TrackState.Pause
                }
            }
        }
    }

}