package com.example.playlistmakerag.data.player

import android.media.MediaPlayer
import com.example.playlistmakerag.domain.PlayerInterface

class Player : PlayerInterface{

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()

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
}