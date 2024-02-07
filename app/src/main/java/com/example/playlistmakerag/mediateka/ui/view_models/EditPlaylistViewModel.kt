package com.example.playlistmakerag.mediateka.ui.view_models

import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type


class EditPlaylistViewModel(private val interactor: PlaylistInteractor): AddPlaylistViewModel(interactor) {
    fun updatePlaylist(playlist: Playlist, newName:String, newDescription:String?, newUrl:String?){
        val newPlaylist = Playlist(newName, newDescription, newUrl, playlist.trackAmount, playlist.addedTracks)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deletePlaylist(playlist)
            interactor.addPlaylist(newPlaylist)
        }
    }
}