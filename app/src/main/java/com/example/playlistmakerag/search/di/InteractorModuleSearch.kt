package com.example.playlistmakerag.search.di

import com.example.playlistmakerag.search.domain.SearchInteractor
import com.example.playlistmakerag.search.domain.impl.SearchInteractorImpl
import org.koin.dsl.module

val interactorModuleSearch = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get(),get())
    }

}