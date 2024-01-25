package com.example.playlistmakerag.mediateka.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.ui.playlist.PlaylistViewHolder
import com.example.playlistmakerag.player.domain.models.Track

class PlaylistAdapter(): RecyclerView.Adapter<PlaylistViewHolder>() {

    var playlists = ArrayList<Playlist>()
    var itemClickListener: ((Int, Playlist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_view, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, playlists[position]) }
    }
}