package com.example.playlistmakerag.mediateka.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.tracks_view, parent, false)) {
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackPicture: ImageView = itemView.findViewById(R.id.placeholderImage)

    fun bind(model: Track) {
        trackName.text = short(model.trackName)
        val time = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        artistName.text = "${short(model.artistName)} • $time"
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.tracks_place_holder)
            .transform(RoundedCorners(10))
            .into(trackPicture)

    }

    private fun short(s: String): String {
        return if (s.length <= 37)
            s
        else
            s.substring(0, 35) + "..."
    }
}