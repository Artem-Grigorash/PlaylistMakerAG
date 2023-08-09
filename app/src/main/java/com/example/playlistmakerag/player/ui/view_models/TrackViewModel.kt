package com.example.playlistmakerag.player.ui.view_models

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmakerag.player.domain.TrackInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewModel(private val interactor: TrackInteractor, url: String) : ViewModel() {
    companion object {
        private const val REFRESH_MILLIS = 200L
    }
     init {
         interactor.setUrl(url)
     }


    private val state = MutableLiveData<TrackState>()
    fun getTrackState(): LiveData<TrackState> = state

    private val time = MutableLiveData<String>()
    fun getTime(): LiveData<String> = time

    private var handler = Handler(Looper.getMainLooper())

    fun onPlayClicked() {
        if (state.value == TrackState.Play)
            state.value = TrackState.Pause
        else
            state.value = TrackState.Play
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
    }

    private fun startTimer() {
        handler.post(
            createUpdateTimerTask()
        )
    }

    private fun createUpdateTimerTask(): Runnable {
        return object : Runnable {
            override fun run() {

                if (state.value == TrackState.Play) {
                    val elapsedTime = interactor.getPosition()
                    val duration = 29700
                    val remainingTime = duration - elapsedTime

                    if (remainingTime > 0) {
                        time.value = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(interactor.getPosition())
                        handler.postDelayed(
                            this,
                            REFRESH_MILLIS
                        )
                    } else {
                        time.value = "00:00"
                        state.value = TrackState.Pause
                    }
                }
            }
        }
    }

}