package com.example.playlistmakerag.search.domain.impl

import com.example.playlistmakerag.search.data.Retrofit
import com.example.playlistmakerag.search.domain.SearchInteractor
import java.util.concurrent.Executors

class SearchInteractorImpl (private val search : Retrofit) : SearchInteractor{

    private val executor = Executors.newCachedThreadPool()
    override fun makeRequest(expression: String, consumer: SearchInteractor.Consumer) {
        executor.execute{
            consumer.consume(search.makeRequest(expression))
        }

    }

}