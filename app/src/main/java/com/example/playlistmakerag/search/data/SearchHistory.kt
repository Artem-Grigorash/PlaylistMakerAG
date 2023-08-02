package com.example.playlistmakerag.search.data

import android.content.SharedPreferences
import com.example.playlistmakerag.creator.Creator.provideSharedPreferences
import com.example.playlistmakerag.player.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val HISTORY_KEY = "key_for_history"

class SearchHistory {

    val pref = provideSharedPreferences()
    
    fun getListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }
    fun read(): ArrayList<Track> {
        val json = pref.getString(HISTORY_KEY, null) ?: return ArrayList()
        return Gson().fromJson(json, object : TypeToken<ArrayList<Track>>() {}.type)
    }

    fun write(tracks: ArrayList<Track>) {
        val json = Gson().toJson(tracks)
        pref.edit()
            .putString(HISTORY_KEY, json)
            .apply()
    }



}