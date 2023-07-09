package com.example.playlistmakerag.player.domain

import android.app.Application
import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.impl.TrackInteractor
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel

//class MyApplication: Application() {
//    private fun getPlayer(url:String): Player {
//        return Player(url)
//    }
//
//    fun provideViewModel(url:String): TrackInteractor {
//        return TrackInteractor(getPlayer(url))
//    }
//}