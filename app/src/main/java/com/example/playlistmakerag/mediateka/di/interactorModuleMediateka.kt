package com.example.playlistmakerag.mediateka.di

import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.impl.PlaylistInteractorImpl
import org.koin.dsl.module

val interactorModuleMediateka = module {

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }

}