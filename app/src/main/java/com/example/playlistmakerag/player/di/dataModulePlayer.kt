package com.example.playlistmakerag.player.di

import android.media.MediaPlayer
import androidx.room.Room
import com.example.playlistmakerag.player.data.db.AppDatabase
import com.example.playlistmakerag.player.data.player.Player
import com.example.playlistmakerag.player.domain.PlayerInterface
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModulePlayer = module {
    factory <PlayerInterface> {
        Player(get())
    }
    factory {
        MediaPlayer()
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
}