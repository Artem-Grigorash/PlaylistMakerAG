package com.example.playlistmakerag.search.data

import android.content.SharedPreferences
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.domain.HistoryInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val HISTORY_KEY = "key_for_history"

class SearchHistory(private val pref: SharedPreferences): HistoryInterface{

    override fun read(): ArrayList<Track> {
        val json = pref.getString(HISTORY_KEY, null) ?: return ArrayList()
        return Gson().fromJson(json, object : TypeToken<ArrayList<Track>>() {}.type)
    }

    override fun write(tracks: ArrayList<Track>) {
        val json = Gson().toJson(tracks)
        pref.edit()
            .putString(HISTORY_KEY, json)
            .apply()
    }
}