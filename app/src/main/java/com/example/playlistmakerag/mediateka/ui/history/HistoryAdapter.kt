package com.example.playlistmakerag.mediateka.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.player.domain.models.Track

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    var tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder = HistoryViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(tracks.get(position))
    }

    override fun getItemCount(): Int = tracks.size
}