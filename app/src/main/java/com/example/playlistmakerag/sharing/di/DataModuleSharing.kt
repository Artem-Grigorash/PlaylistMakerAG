package com.example.playlistmakerag.sharing.di

import com.example.playlistmakerag.sharing.data.ExternalNavigator
import com.example.playlistmakerag.sharing.domain.ExternalNavigatorInterface
import org.koin.dsl.module

val dataModuleSharing = module {
    single<ExternalNavigatorInterface> {
        ExternalNavigator()
    }

}