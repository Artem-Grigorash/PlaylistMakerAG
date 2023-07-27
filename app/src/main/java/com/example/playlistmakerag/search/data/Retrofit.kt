package com.example.playlistmakerag.search.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.player.data.dto.ItunesApi
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInterface
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit : SearchInterface {

    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ItunesApi::class.java)

    override fun makeRequest(text: String): Response<TrackResponse>? {
        return try {
            trackService.search(text).execute()
        } catch (e : Exception){
            null
        }
    }
    fun provideSharedPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences(PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }
}