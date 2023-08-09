package com.example.playlistmakerag.settings.di


import com.example.playlistmakerag.settings.domain.SettingsInteractor
import com.example.playlistmakerag.settings.domain.impl.SettingsInteractorImpl
import org.koin.dsl.module

val interactorModuleSettings = module {

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

}