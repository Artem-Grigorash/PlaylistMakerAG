package com.example.playlistmakerag.mediateka.ui.playlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.models.Playlist

class PlaylistViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val amountOfTracks: TextView = itemView.findViewById(R.id.description)
    private val picture: ImageView = itemView.findViewById(R.id.trackPicture)

    fun bind(playlist: Playlist) {
        title.text = playlist.playlistName
        amountOfTracks.text = "${ playlist.trackAmount.toString() } треков"
        if(playlist.imageUri!=null)
            Glide.with(itemView)
                .load(playlist.imageUri)
                .placeholder(R.drawable.tracks_place_holder)
//                .transform(RoundedCorners(R.dimen.small_corner_radius))
                .into(picture)
    }
}