package com.example.playlistmakerag.search.data


import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.ItunesApi
import com.example.playlistmakerag.search.domain.SearchInterface
import java.util.ArrayList

class RetrofitProvider (private val trackService: ItunesApi): SearchInterface {

    override fun makeRequest(text: String): ArrayList<Track>? {
        return try {
            val resp = trackService.search(text).execute()
            resp.body()?.results
        } catch (e: Exception) {
            ArrayList<Track>()
        }
    }

}