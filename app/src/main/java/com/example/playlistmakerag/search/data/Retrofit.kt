package com.example.playlistmakerag.search.data

import android.view.View
import android.widget.EditText
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.data.dto.ItunesApi
import com.example.playlistmakerag.player.data.dto.TrackResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    //data
    private val baseUrl = "https://itunes.apple.com"


    //data
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //data
    private val trackService = retrofit.create(ItunesApi::class.java)

    private fun searchTracks(inputEditText: EditText){
        if (inputEditText.text.isNotEmpty()) {
            trackService.search(inputEditText.text.toString()).enqueue(object :
                Callback<TrackResponse> {
                override fun onResponse(call: Call<TrackResponse>,
                                        response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        tracks.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                        //состояние 1
                        }
                        if (tracks.isEmpty()) {
                            //состояние 2
                        }
                    }

                    else {
                        //состояние 3
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    //состояние 4
                }

            })
        }
        else{
            //состояние 5
        }
    }
}