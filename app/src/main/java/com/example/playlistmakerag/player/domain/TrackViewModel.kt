package com.example.playlistmakerag.player.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.player.domain.impl.TrackInteractor

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TrackInteractor,
) : ViewModel() {

    private var screenStateLiveData = MutableLiveData<TrackState>(TrackState.Loading)

    init {
        tracksInteractor.loadTrackData(
            trackId = trackId,
            onComplete = { trackModel ->
                // 1
                screenStateLiveData.postValue(
                    TrackState.Content(trackModel)
                )
            }
        )
    }

    fun getScreenStateLiveData(): LiveData<TrackState> = screenStateLiveData

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[APPLICATION_KEY] as MyApplication).provideTracksInteractor()

                TrackViewModel(
                    trackId,
                    interactor,
                )
            }
        }
    }
}