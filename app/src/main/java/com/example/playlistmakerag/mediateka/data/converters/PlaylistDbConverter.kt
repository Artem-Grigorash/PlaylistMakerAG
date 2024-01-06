package com.example.playlistmakerag.mediateka.data.converters

import com.example.playlistmakerag.mediateka.data.db.entity.PlaylistEntity
import com.example.playlistmakerag.mediateka.domain.models.Playlist

class PlaylistDbConverter {

        fun map(playlist: Playlist): PlaylistEntity {
            return PlaylistEntity(playlist.playlistName, playlist.playlistDescription, playlist.imageUri, playlist.trackAmount, playlist.addedTracks)
        }
        fun map(playlist: PlaylistEntity): Playlist {
            return Playlist(playlist.playlistName, playlist.playlistDescription, playlist.imageUri, playlist.trackAmount, playlist.addedTracks)
        }

}