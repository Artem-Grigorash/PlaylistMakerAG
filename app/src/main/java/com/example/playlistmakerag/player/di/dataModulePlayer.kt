package com.example.playlistmakerag.player.di

import android.media.MediaPlayer
import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.PlayerInterface
import org.koin.dsl.module

val dataModulePlayer = module {
    factory <PlayerInterface> {
        Player(get())
    }
    factory {
        MediaPlayer()
    }
}