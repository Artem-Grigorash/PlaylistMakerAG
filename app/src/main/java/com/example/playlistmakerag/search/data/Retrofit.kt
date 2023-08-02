package com.example.playlistmakerag.search.data


import com.example.playlistmakerag.search.data.dto.ItunesApi
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit : SearchInterface {

    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ItunesApi::class.java)

    override fun makeRequest(text: String): TrackResponse? {
        return try {
            val resp = trackService.search(text).execute()
            TrackResponse(resp.code(), resp.body()?.results)
        } catch (e: Exception) {
            null
        }
    }

}