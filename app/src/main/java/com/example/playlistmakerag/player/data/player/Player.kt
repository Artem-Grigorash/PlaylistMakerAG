package com.example.playlistmakerag.player.data.player

import android.media.MediaPlayer
import com.example.playlistmakerag.player.domain.PlayerInterface

class Player(private val mediaPlayer: MediaPlayer) : PlayerInterface {

    companion object {
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }


    private var playerState = STATE_PAUSED


    override fun getPosition(): Int = mediaPlayer.currentPosition

    override fun delete() {
        mediaPlayer.release()
    }

    override fun setUrl(url: String){
        mediaPlayer.setDataSource(url)
        this.preparePlayer()
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
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

}