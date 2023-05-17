package com.example.playlistmakerag.data.player

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.example.playlistmakerag.domain.PlayerInterface
import com.example.playlistmakerag.presentation.track.TrackPresenter
import java.text.SimpleDateFormat
import java.util.*

class Player(url:String) : PlayerInterface{

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()

    init {
        mediaPlayer.setDataSource(url)
        this.preparePlayer()
    }

    override fun getPosition(): Int = mediaPlayer.currentPosition

    override fun delete() {
        mediaPlayer.release()
    }

    fun release(){

    }

    private fun preparePlayer() {
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    override fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }



}