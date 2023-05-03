package com.example.playlistmakerag.data.player

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.example.playlistmakerag.domain.PlayerInterface
import com.example.playlistmakerag.presentation.track.TrackPresenter
import java.text.SimpleDateFormat
import java.util.*

class Player : PlayerInterface{

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val REFRESH_MILLIS = 200L
    }
    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()
    private var handler = Handler(Looper.getMainLooper())

    override fun preparePlayer() {
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = Player.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = Player.STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = Player.STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = Player.STATE_PAUSED
    }

    override fun playbackControl() {
        when(playerState) {
            Player.STATE_PLAYING -> {
                pausePlayer()
            }
            Player.STATE_PREPARED, Player.STATE_PAUSED -> {
                startPlayer()
            }
        }
    }


    override fun startTimer(progress: TextView) {
        handler.post(
            createUpdateTimerTask(progress)
        )
    }

    private fun createUpdateTimerTask(progress: TextView): Runnable {
        return object : Runnable {
            override fun run() {

                if(playerState == Player.STATE_PLAYING){
                    val elapsedTime = mediaPlayer.currentPosition
                    val duration = 29700
                    val remainingTime = duration - elapsedTime

                    if (remainingTime > 0) {
                        progress.text = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(mediaPlayer.currentPosition)
                        handler.postDelayed(this,
                            Player.REFRESH_MILLIS
                        )
                    } else {
                        progress.text = "00:00"
                    }
                }
            }
        }
    }
}