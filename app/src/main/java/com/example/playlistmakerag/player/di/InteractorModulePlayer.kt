package com.example.playlistmakerag.player.di

import com.example.playlistmakerag.player.domain.TrackInteractor
import com.example.playlistmakerag.player.domain.impl.TrackInteractorImpl
import org.koin.dsl.module

val interactorModulePlayer = module {

    factory <TrackInteractor> {
        TrackInteractorImpl(get())
    }

}