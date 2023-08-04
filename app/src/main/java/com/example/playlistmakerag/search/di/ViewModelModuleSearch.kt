package com.example.playlistmakerag.search.di

import com.example.playlistmakerag.search.ui.view_models.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleSearch = module {

    viewModel {
        SearchViewModel(get())
    }

}