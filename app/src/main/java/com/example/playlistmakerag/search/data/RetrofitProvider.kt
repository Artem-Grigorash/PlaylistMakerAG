package com.example.playlistmakerag.search.data


import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.dto.ItunesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class RetrofitProvider (private val trackService: ItunesApi): SearchInterface {

    override suspend fun makeRequest(text: String): ArrayList<Track>? {

        return withContext(Dispatchers.IO) {
            try {
                val response = trackService.search(text)
                response.results
            } catch (e: Throwable) {
                ArrayList<Track>()
            }
        }
    }

}