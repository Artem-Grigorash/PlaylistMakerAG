package com.example.playlistmakerag.player.di

import com.example.playlistmakerag.player.data.converters.TrackDbConvertor
import com.example.playlistmakerag.player.data.db.HistoryRepositoryImpl
import com.example.playlistmakerag.player.domain.db.HistoryRepository
import com.example.playlistmakerag.search.data.TrackRepositoryImpl
import com.example.playlistmakerag.search.domain.TrackRepository
import org.koin.dsl.module


val repositoryModule = module {


    factory { TrackDbConvertor() }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }

}