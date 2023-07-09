package com.example.playlistmakerag.player.domain.view_models

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
import com.example.playlistmakerag.R
import com.example.playlistmakerag.creator.Creator
import com.example.playlistmakerag.player.domain.MyApplication
import com.example.playlistmakerag.player.domain.TrackState
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewModel(private val interactor: TrackInteractor): ViewModel() {
    companion object {
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val REFRESH_MILLIS = 200L

        fun getViewModelFactory(url: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[APPLICATION_KEY] as MyApplication).provideViewModel(url)
                TrackViewModel(
                    interactor
                )
            }
        }
    }

    private val state = MutableLiveData<TrackState>()
    fun getState() : LiveData<TrackState> = state


    private var playerState = TrackViewModel.STATE_PAUSED
    private var handler = Handler(Looper.getMainLooper())

    fun onArrayBackClicked(activity: Activity){
        activity.finish()
    }

    fun onPlayClicked(){
        if (state.value==TrackState.Pause)
            state.value = TrackState.Play
        else
            state.value = TrackState.Pause
    }


    fun delete(){
        interactor.delete()
    }

    fun preparePlayer(play: ImageButton) {
        play.isEnabled = true
        play.setImageResource(R.drawable.play)
    }

    fun startPlayer(play: ImageButton, progress: TextView) {
        startTimer(play, progress)
        play.setImageResource(R.drawable.pause)
    }

    fun pausePlayer(play: ImageButton) {
        play.setImageResource(R.drawable.play)
    }

//    private fun playbackControl(play: ImageButton, progress: TextView) {
//        interactor.playbackControl()
//        when(playerState) {
//            STATE_PLAYING -> {
//                pausePlayer(play)
//            }
//            STATE_PAUSED -> {
//                startPlayer(play, progress)
//            }
//        }
//    }
    private fun startTimer(play: ImageButton, progress: TextView) {
        handler.post(
            createUpdateTimerTask(play, progress)
        )
    }

    private fun createUpdateTimerTask(play: ImageButton, progress: TextView): Runnable {
        return object : Runnable {
            override fun run() {

                if(playerState == STATE_PLAYING){
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