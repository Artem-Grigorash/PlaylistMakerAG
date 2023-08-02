package com.example.playlistmakerag.search.domain.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.Retrofit
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.domain.SearchInteractor
import java.util.concurrent.Executors

class SearchInteractorImpl(private val search: Retrofit, private val history: SearchHistory) : SearchInteractor {

    private val executor = Executors.newCachedThreadPool()
    override fun makeRequest(expression: String, consumer: SearchInteractor.Consumer) {
        executor.execute {
            val searchResult = search.makeRequest(expression)
            if (searchResult != null) {
                consumer.consume(searchResult)
            }
        }
    }

    override fun read(): ArrayList<Track>{
        return history.read()
    }

    override fun write(tracks: ArrayList<Track>){
        history.write(tracks)
    }

}