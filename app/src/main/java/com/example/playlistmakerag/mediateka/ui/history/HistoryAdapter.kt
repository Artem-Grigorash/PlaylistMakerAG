package com.example.playlistmakerag.mediateka.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.search.ui.TracksViewHolder

class HistoryAdapter : RecyclerView.Adapter<TracksViewHolder>() {

    var tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder = TracksViewHolder(parent)

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(tracks.get(position))
    }

    override fun getItemCount(): Int = tracks.size
}