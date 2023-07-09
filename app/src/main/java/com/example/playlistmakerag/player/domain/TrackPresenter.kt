package com.example.playlistmakerag.player.domain
//
//import android.app.Activity
//import android.app.Application
//import android.os.Handler
//import android.os.Looper
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.ViewModel
//import com.example.playlistmakerag.R
//import com.example.playlistmakerag.creator.Creator
//import com.example.playlistmakerag.player.domain.impl.TrackInteractor
//import java.text.SimpleDateFormat
//import java.util.*
//
//class TrackPresenter (
//        private val interactor: TrackInteractor,
//        application: Application
//) : AndroidViewModel(application){
//        companion object {
//                private const val STATE_PLAYING = 2
//                private const val STATE_PAUSED = 3
//                private const val REFRESH_MILLIS = 200L
//        }
//        private var playerState = STATE_PAUSED
//        private var handler = Handler(Looper.getMainLooper())
//
//
//        fun onArrayBackClicked(activity: Activity){
//                activity.finish()
//        }
//
////        fun onPlayClicked(play: ImageButton, progress: TextView){
////                playbackControl(play, progress)
////        }
//
//
//        fun delete(){
//                interactor.delete()
//        }
//
//        fun preparePlayer(play: ImageButton) {
//                play.isEnabled = true
//                play.setImageResource(R.drawable.play)
//        }
//
//        private fun startPlayer(play: ImageButton, progress: TextView) {
//                startTimer(play, progress)
//                play.setImageResource(R.drawable.pause)
//                playerState = STATE_PLAYING
//        }
//
//        private fun pausePlayer(play: ImageButton) {
//                play.setImageResource(R.drawable.play)
//                playerState = STATE_PAUSED
//        }
//
//        private fun playbackControl(play: ImageButton, progress: TextView) {
//                interactor.playbackControl()
//                when(playerState) {
//                        STATE_PLAYING -> {
//                                pausePlayer(play)
//                        }
//                        STATE_PAUSED -> {
//                                startPlayer(play, progress)
//                        }
//                }
//        }
//        private fun startTimer(play: ImageButton, progress: TextView) {
//                handler.post(
//                        createUpdateTimerTask(play, progress)
//                )
//        }
//
//        private fun createUpdateTimerTask(play: ImageButton, progress: TextView): Runnable {
//                return object : Runnable {
//                        override fun run() {
//
//                                if(playerState == STATE_PLAYING){
//                                        val elapsedTime = interactor.getPosition()
//                                        val duration = 29700
//                                        val remainingTime = duration - elapsedTime
//
//                                        if (remainingTime > 0) {
//                                                progress.text = SimpleDateFormat(
//                                                        "mm:ss",
//                                                        Locale.getDefault()
//                                                ).format(interactor.getPosition())
//                                                handler.postDelayed(this,
//                                                        REFRESH_MILLIS
//                                                )
//                                        } else {
//                                                progress.text = "00:00"
//                                                play.setImageResource(R.drawable.play)
//                                        }
//                                }
//                        }
//                }
//        }
//
//
//
//}