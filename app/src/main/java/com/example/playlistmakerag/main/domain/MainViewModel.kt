package com.example.playlistmakerag.main.domain

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmakerag.player.domain.TrackState
import com.example.playlistmakerag.search.ui.SearchActivity

class MainViewModel : ViewModel(){
    private val state = MutableLiveData<TrackState>()
    fun getState() : LiveData<TrackState> = state

//    fun onSearchClicked(context: ComponentActivity){
//        val intent = Intent(context, SearchActivity::class.java)
//        startActivity(intent)
//    }

}