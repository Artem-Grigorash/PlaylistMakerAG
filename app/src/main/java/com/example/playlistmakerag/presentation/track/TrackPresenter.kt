package com.example.playlistmakerag.presentation.track

import android.app.Activity
import android.media.MediaPlayer
import android.os.Handler
import android.widget.ImageButton
import android.widget.TextView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.domain.TrackInteractor
import java.text.SimpleDateFormat
import java.util.*

class TrackPresenter (
        private val interactor: TrackInteractor
){
        companion object {
                private const val STATE_DEFAULT = 0
                private const val STATE_PREPARED = 1
                private const val STATE_PLAYING = 2
                private const val STATE_PAUSED = 3
                private const val REFRESH_MILLIS = 200L
        }
        private var playerState = STATE_DEFAULT

        fun onArrayBackClicked(activity: Activity){
                activity.finish()
        }

        fun onPlayClicked(play: ImageButton, progress: TextView){
                interactor.playbackControl()
                playbackControl(play, progress)
        }



        fun preparePlayer(play: ImageButton) {
                interactor.preparePlayer()
                play.isEnabled = true
                play.setImageResource(R.drawable.play)
        }

        private fun startPlayer(play: ImageButton, progress: TextView) {
                interactor.startTimer(progress)
                play.setImageResource(R.drawable.pause)
                playerState = STATE_PLAYING
        }

        fun pausePlayer(play: ImageButton) {
                play.setImageResource(R.drawable.play)
                playerState = STATE_PAUSED
        }

        fun playbackControl(play: ImageButton, progress: TextView) {
                interactor.playbackControl()
                when(playerState) {
                        STATE_PLAYING -> {
                                pausePlayer(play)
                        }
                        STATE_PREPARED, STATE_PAUSED -> {
                                startPlayer(play, progress)
                        }
                }
        }
}