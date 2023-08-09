package com.example.playlistmakerag.search.di

import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.search.data.RetrofitProvider
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.data.dto.ItunesApi
import com.example.playlistmakerag.search.domain.HistoryInterface
import com.example.playlistmakerag.search.domain.SearchInterface
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModuleSearch = module {

    single<ItunesApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApi::class.java)
    }

    factory { Gson() }

    single<SearchInterface> {
        RetrofitProvider(get())
    }


    single<HistoryInterface> {
        SearchHistory(get())
    }
    single {
        androidContext()
            .getSharedPreferences(PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }

}