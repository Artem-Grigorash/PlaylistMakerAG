package com.example.playlistmakerag.mediateka.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.models.Playlist

class PlaylistViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)

    fun bind(playlist: Playlist) {
        title.text = playlist.playlistName
        description.text = playlist.playlistDescription
    }
}