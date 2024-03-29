package com.example.playlistmakerag.mediateka.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.models.Playlist

class PlaylistOnTrackAdapter(): RecyclerView.Adapter<PlaylistOnTrackViewHolder>() {

    var playlists = ArrayList<Playlist>()

    var itemClickListener: ((Int, Playlist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistOnTrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.small_playlist_view, parent, false)
        return PlaylistOnTrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistOnTrackViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.bind(playlist)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, playlist) }
    }
}