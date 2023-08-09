package com.example.playlistmakerag.sharing.di

import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.example.playlistmakerag.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModuleSharing = module {

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

}