package com.example.playlistmakerag.mediateka.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.player.domain.models.Track

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    var tracks = ArrayList<Track>()
    var itemClickListener: ((Int, Track) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder = HistoryViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, track) }
    }

    override fun getItemCount(): Int = tracks.size

}