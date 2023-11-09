package com.example.playlistmakerag.mediateka.ui.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AddPlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    fun savePlaylist(name: String, description: String?, uri: Uri?){
            viewModelScope.launch {
                interactor.addPlaylist(createPlaylist(name, description, uri))
            }
        }

    private fun createPlaylist(name: String, description: String?, uri: Uri?): Playlist{
        return Playlist(name,description, uri.toString(), 0, "")
    }
}