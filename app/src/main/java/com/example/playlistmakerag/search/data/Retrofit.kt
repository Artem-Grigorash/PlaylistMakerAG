package com.example.playlistmakerag.search.data

import com.example.playlistmakerag.player.data.dto.ItunesApi
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit: SearchInterface {
    //data
    private val baseUrl = "https://itunes.apple.com"

    //data
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //data
    private val trackService = retrofit.create(ItunesApi::class.java)

    override fun makeRequest(text: String): Response<TrackResponse> {
        return trackService.search(text).execute()

//            val resp = trackService.search(text).execute()
//
//            val body = resp.body() ?: Response()
//
//            return body.apply { resultCode = resp.code() }

    }
}