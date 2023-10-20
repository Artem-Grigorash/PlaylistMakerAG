package com.example.playlistmakerag.mediateka.di

import com.example.playlistmakerag.mediateka.ui.view_models.AddPlaylistViewModel
import com.example.playlistmakerag.mediateka.ui.view_models.FavouriteTracksViewModel
import com.example.playlistmakerag.mediateka.ui.view_models.HistoryViewModel
import com.example.playlistmakerag.mediateka.ui.view_models.PlaylistsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleMediateka = module {

    viewModel {
        FavouriteTracksViewModel()
    }

    viewModel {
        PlaylistsViewModel(androidContext(), get())
    }
    viewModel {
        HistoryViewModel(androidContext(), get())
    }

    viewModel{
        AddPlaylistViewModel(get())
    }
}