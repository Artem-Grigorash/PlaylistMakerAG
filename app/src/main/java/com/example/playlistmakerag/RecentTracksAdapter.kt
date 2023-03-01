package com.example.playlistmakerag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentTracksAdapter(
    private val recentTracks: ArrayList<Track>
) : RecyclerView.Adapter<TracksViewHolder> (){

    var itemClickListener: ((Int, Track) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracks_view, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = recentTracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener{itemClickListener?.invoke(position, track)}
    }

    override fun getItemCount(): Int {
        return recentTracks.size
    }

}