package com.example.playlistmakerag.player.di

import com.example.playlistmakerag.player.ui.view_models.TrackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModulePlayer = module {

    viewModel {(url: String)->
        TrackViewModel(get(),get(),url)
    }

}