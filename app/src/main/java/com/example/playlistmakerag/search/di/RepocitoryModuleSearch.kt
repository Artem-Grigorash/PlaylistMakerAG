package com.example.playlistmakerag.search.di

import com.example.playlistmakerag.search.data.RetrofitProvider
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.domain.HistoryInterface
import com.example.playlistmakerag.search.domain.SearchInterface
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchInterface> {
        RetrofitProvider(get())
    }

    single<HistoryInterface> {
        SearchHistory(get())
    }
}