package com.example.playlistmakerag.player.ui.view_models

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewModel(private val interactor: TrackInteractor): ViewModel() {
    companion object {
        private const val REFRESH_MILLIS = 200L

        fun getViewModelFactory(url: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[APPLICATION_KEY] as App).provideViewModel(url)
                TrackViewModel(
                    interactor
                )
            }
        }
    }

    private val state = MutableLiveData<TrackState>()
    fun getTrackState() : LiveData<TrackState> = state

    private var handler = Handler(Looper.getMainLooper())

    fun onArrayBackClicked(activity: Activity){
        activity.finish()
    }

    fun onPlayClicked(){
        if (state.value== TrackState.Play)
            state.value = TrackState.Pause
        else
            state.value = TrackState.Play
    }

    fun delete(){
        interactor.delete()
    }

    fun preparePlayer(play: ImageButton) {
        play.isEnabled = true
        play.setImageResource(R.drawable.play)
    }

    fun startPlayer(play: ImageButton, progress: TextView) {
        interactor.playbackControl()
        startTimer(play, progress)
        play.setImageResource(R.drawable.pause)
    }

    fun pausePlayer(play: ImageButton) {
        interactor.playbackControl()
        play.setImageResource(R.drawable.play)
    }

    private fun startTimer(play: ImageButton, progress: TextView) {
        handler.post(
            createUpdateTimerTask(play, progress)
        )
    }

    private fun createUpdateTimerTask(play: ImageButton, progress: TextView): Runnable {
        return object : Runnable {
            override fun run() {

                if (state.value== TrackState.Play){
                    val elapsedTime = interactor.getPosition()
                    val duration = 29700
                    val remainingTime = duration - elapsedTime

                    if (remainingTime > 0) {
                        progress.text = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(interactor.getPosition())
                        handler.postDelayed(this,
                            REFRESH_MILLIS
                        )
                    } else {
                        progress.text = "00:00"
                        play.setImageResource(R.drawable.play)
                    }
                }
            }
        }
    }

}