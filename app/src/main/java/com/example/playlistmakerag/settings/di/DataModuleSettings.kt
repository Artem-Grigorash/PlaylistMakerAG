package com.example.playlistmakerag.settings.di


import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.settings.data.SwitchTheme
import com.example.playlistmakerag.settings.domain.SwitchThemeInterface
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModuleSettings = module {

    single<SwitchThemeInterface> {
        SwitchTheme(get(),androidContext())
    }

    single {
        androidContext()
            .getSharedPreferences(PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }


}