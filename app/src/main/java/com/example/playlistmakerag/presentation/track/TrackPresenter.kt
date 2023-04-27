package com.example.playlistmakerag.presentation.track

import android.app.Activity
import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.data.dto.Track
import com.example.playlistmakerag.ui.track.TrackDisplayActivity
import java.text.SimpleDateFormat
import java.util.*

class TrackPresenter (
        private val view : TrackView
){
        companion object {
                private const val STATE_DEFAULT = 0
                private const val STATE_PREPARED = 1
                private const val STATE_PLAYING = 2
                private const val STATE_PAUSED = 3
                private const val REFRESH_MILLIS = 200L
        }

        private var playerState = TrackPresenter.STATE_DEFAULT



        fun onArrayBackClicked(activity: Activity){
                activity.finish()
        }
        fun onPlayClicked(){

        }

        private fun startTimer(mediaPlayer: MediaPlayer, handler: Handler?, progress: TextView) {
                handler?.post(
                        createUpdateTimerTask(mediaPlayer, handler, progress)
                )
        }



        private fun createUpdateTimerTask(mediaPlayer: MediaPlayer, handler: Handler?, progress: TextView): Runnable {
                return object : Runnable {
                        override fun run() {

                                if(playerState == TrackPresenter.STATE_PLAYING){
                                        val elapsedTime = mediaPlayer.currentPosition
                                        val duration = 29700
                                        val remainingTime = duration - elapsedTime

                                        if (remainingTime > 0) {
                                                progress.text = SimpleDateFormat(
                                                        "mm:ss",
                                                        Locale.getDefault()
                                                ).format(mediaPlayer.currentPosition)
                                                handler?.postDelayed(this,
                                                        TrackPresenter.REFRESH_MILLIS
                                                )
                                        } else {
                                                progress.text = "00:00"
                                        }
                                }
                        }
                }
        }

        fun setInfo(
                track: Track,
                name: TextView,
                author: TextView,
                time: TextView,
                album: TextView,
                year: TextView,
                genre: TextView,
                country: TextView,
                picture: ImageView
        ){
                name.text = track.trackName
                author.text = track.artistName
                val timer = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                time.text = timer
                if (track.collectionName!=null)
                        album.text = track.collectionName
                else
                        album.visibility = View.GONE
                year.text = track.releaseDate.substring(0,4)
                genre.text = track.primaryGenreName
                country.text = track.country
                Glide.with(picture)
                        .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
                        .placeholder(R.drawable.tracks_place_holder)
                        .transform(RoundedCorners(30))
                        .into(picture)
        }

        fun preparePlayer(mediaPlayer:MediaPlayer, play: ImageButton) {
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                        play.isEnabled = true
                        playerState = TrackPresenter.STATE_PREPARED
                }
                mediaPlayer.setOnCompletionListener {
                        play.setImageResource(R.drawable.play)
                        playerState = TrackPresenter.STATE_PREPARED
                }
        }

        private fun startPlayer(mediaPlayer:MediaPlayer, play: ImageButton, handler: Handler?, progress: TextView) {
                mediaPlayer.start()
                startTimer(mediaPlayer, handler, progress)
                play.setImageResource(R.drawable.pause)
                playerState = TrackPresenter.STATE_PLAYING
        }

        private fun pausePlayer(mediaPlayer: MediaPlayer, play: ImageButton) {
                mediaPlayer.pause()

                play.setImageResource(R.drawable.play)
                playerState = TrackPresenter.STATE_PAUSED
        }

        fun onPause(mediaPlayer: MediaPlayer, play: ImageButton) {
                pausePlayer(mediaPlayer, play)
        }

        fun onDestroy(mediaPlayer: MediaPlayer) {
                mediaPlayer.release()

        }

        fun playbackControl(mediaPlayer:MediaPlayer, play: ImageButton, handler: Handler?, progress: TextView) {
                when(playerState) {
                        TrackPresenter.STATE_PLAYING -> {
                                pausePlayer(mediaPlayer, play)
                        }
                        TrackPresenter.STATE_PREPARED, TrackPresenter.STATE_PAUSED -> {
                                startPlayer(mediaPlayer, play, handler, progress)
                        }
                }
        }
}