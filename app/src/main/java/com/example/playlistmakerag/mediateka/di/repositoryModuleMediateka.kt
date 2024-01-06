package com.example.playlistmakerag.mediateka.di

import com.example.playlistmakerag.mediateka.data.PlaylistRepositoryImpl
import com.example.playlistmakerag.mediateka.data.converters.PlaylistDbConverter
import com.example.playlistmakerag.mediateka.domain.db.PlaylistRepository
import org.koin.dsl.module

val repositoryModuleMediateka = module {

    factory { PlaylistDbConverter() }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(get(), get())
    }

}