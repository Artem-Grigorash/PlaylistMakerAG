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

        fun onPlayClicked(play: ImageButton, handler: Handler, progress: TextView){
                interactor.playbackControl()
        }

        private fun startTimer(handler: Handler, progress: TextView) {
                handler.post(
                        createUpdateTimerTask(mediaPlayer, handler, progress)
                )
        }

        private fun createUpdateTimerTask(handler: Handler, progress: TextView): Runnable {
                return object : Runnable {
                        override fun run() {

                                if(playerState == STATE_PLAYING){
                                        val elapsedTime = mediaPlayer.currentPosition
                                        val duration = 29700
                                        val remainingTime = duration - elapsedTime

                                        if (remainingTime > 0) {
                                                progress.text = SimpleDateFormat(
                                                        "mm:ss",
                                                        Locale.getDefault()
                                                ).format(mediaPlayer.currentPosition)
                                                handler.postDelayed(this,
                                                        REFRESH_MILLIS
                                                )
                                        } else {
                                                progress.text = "00:00"
                                        }
                                }
                        }
                }
        }

        fun preparePlayer(play: ImageButton) {
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                        play.isEnabled = true
                        playerState = STATE_PREPARED
                }
                mediaPlayer.setOnCompletionListener {
                        play.setImageResource(R.drawable.play)
                        playerState = STATE_PREPARED
                }
        }

        private fun startPlayer(play: ImageButton, handler: Handler, progress: TextView) {
                mediaPlayer.start()
                startTimer(mediaPlayer, handler, progress)
                play.setImageResource(R.drawable.pause)
                playerState = STATE_PLAYING
        }

        fun pausePlayer(play: ImageButton) {
                mediaPlayer.pause()

                play.setImageResource(R.drawable.play)
                playerState = STATE_PAUSED
        }

        fun playbackControl(play: ImageButton, handler: Handler, progress: TextView) {
                when(playerState) {
                        STATE_PLAYING -> {
                                pausePlayer(mediaPlayer, play)
                        }
                        STATE_PREPARED, STATE_PAUSED -> {
                                startPlayer(mediaPlayer, play, handler, progress)
                        }
                }
        }
}