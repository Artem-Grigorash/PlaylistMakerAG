package com.example.playlistmakerag.settings.di

import com.example.playlistmakerag.settings.ui.view_models.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleSettings = module {

    viewModel {
        SettingsViewModel(get(),get())
    }

}