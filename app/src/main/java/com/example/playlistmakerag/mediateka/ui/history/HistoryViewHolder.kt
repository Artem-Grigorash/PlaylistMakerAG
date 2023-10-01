package com.example.playlistmakerag.mediateka.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.models.Track

class HistoryViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)) {

    var cover: ImageView = itemView.findViewById(R.id.cover)
    var title: TextView = itemView.findViewById(R.id.title)
    var description: TextView = itemView.findViewById(R.id.description)

    fun bind(track: Track) {
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .into(cover)

        title.text = track.trackName
        description.text = movie.description
    }
}