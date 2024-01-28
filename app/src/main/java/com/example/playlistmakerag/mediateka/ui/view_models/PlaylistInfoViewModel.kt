package com.example.playlistmakerag.mediateka.ui.view_models

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.sharing.domain.SharingInteractor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class PlaylistInfoViewModel(private val playlistInteractor: PlaylistInteractor, private val sharingInteractor: SharingInteractor): ViewModel() {

    private var isClickAllowed = true
    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(1000L)
                isClickAllowed = true
            }
        }
        return current
    }

    fun shareApp(link: String): Intent {
        return sharingInteractor.shareApp(link)
    }

    fun deleteTrack(playlist: Playlist, actualTrack: Track){
        val listType: Type = object : TypeToken<ArrayList<Track?>?>() {}.type
        val tracks: ArrayList<Track>? = Gson().fromJson(playlist.addedTracks, listType)
        if (tracks != null) {
            tracks.remove(actualTrack)
        val newString = Gson().toJson(tracks)
        val newPlaylist = Playlist(playlist.playlistName, playlist.playlistDescription, playlist.imageUri,
            playlist.trackAmount?.minus(1), newString)
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.updatePlaylist(newPlaylist)
//            fillData()
//            message.postValue("Добавлено в плейлист ${playlist.playlistName}")
        }
    }
    }
    private val stateLiveData = MutableLiveData<List<Playlist>>()

    fun observeState(): LiveData<List<Playlist>> = stateLiveData

    private fun fillData() {
        viewModelScope.launch {
            playlistInteractor
                .historyPlaylists()
                .collect { playlist ->
                    stateLiveData.postValue(playlist)
                }
        }
    }

}