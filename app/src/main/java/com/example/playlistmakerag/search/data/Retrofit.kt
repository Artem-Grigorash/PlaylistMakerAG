package com.example.playlistmakerag.search.data


import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.ItunesApi
import com.example.playlistmakerag.search.data.dto.TrackResponse
import com.example.playlistmakerag.search.domain.SearchInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class Retrofit : SearchInterface {

    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ItunesApi::class.java)

    override fun makeRequest(text: String): ArrayList<Track>? {
        return try {
            val resp = trackService.search(text).execute()
            resp.body()?.results
        } catch (e: Exception) {
            ArrayList<Track>()
        }
    }

}