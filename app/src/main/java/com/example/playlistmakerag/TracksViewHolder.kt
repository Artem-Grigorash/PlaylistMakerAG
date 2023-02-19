package com.example.playlistmakerag

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*

class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackPicture: ImageView = itemView.findViewById(R.id.trackPicture)

    fun bind(model: Track){
        trackName.text = model.trackName
        val time = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        artistName.text = "${model.artistName} • $time"
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.tracks_place_holder)
            .transform(RoundedCorners(10))
            .into(trackPicture)

    }
}