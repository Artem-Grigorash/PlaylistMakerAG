package com.example.playlistmakerag.presentation.track

class TrackPresenter (
        private val view : TrackView
){
        fun onArrayBackClicked(){
//                view.finish()
        }
        fun onPlayClicked(){
                view.playbackControl()
        }
}