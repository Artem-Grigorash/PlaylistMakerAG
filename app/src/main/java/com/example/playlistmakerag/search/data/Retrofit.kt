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
        var resp: Response<TrackResponse>
        trackService.search(text).enqueue(object :
            Callback<TrackResponse> {
            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {
                resp = response
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {

            }
        })
        return resp
    }
}