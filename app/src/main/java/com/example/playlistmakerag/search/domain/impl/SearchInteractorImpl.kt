package com.example.playlistmakerag.search.domain.impl

import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.data.RetrofitProvider
import com.example.playlistmakerag.search.data.SearchHistory
import com.example.playlistmakerag.search.domain.HistoryInterface
import com.example.playlistmakerag.search.domain.SearchInteractor
import com.example.playlistmakerag.search.domain.SearchInterface
import java.util.concurrent.Executors

class SearchInteractorImpl(private val search: SearchInterface, private val history: HistoryInterface) :
    SearchInteractor {

    var responseIsEmpty = false

    override fun getResponseState(): Boolean {
        return responseIsEmpty
    }

    private val executor = Executors.newCachedThreadPool()
    override fun makeRequest(expression: String, consumer: SearchInteractor.Consumer) {
        executor.execute {
            val searchResult = search.makeRequest(expression)
            if (searchResult != null) {
                responseIsEmpty = false
                consumer.consume(searchResult)
            } else {
                responseIsEmpty = true
            }
        }
    }

    override fun read(): ArrayList<Track> {
        return history.read()
    }

    override fun write(tracks: ArrayList<Track>) {
        history.write(tracks)
    }

}