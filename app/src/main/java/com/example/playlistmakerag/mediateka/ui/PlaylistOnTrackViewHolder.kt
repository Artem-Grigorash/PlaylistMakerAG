package com.example.playlistmakerag.mediateka.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.mediateka.domain.models.Playlist

class PlaylistOnTrackViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val picture: ImageView = itemView.findViewById(R.id.trackPicture)

    fun bind(playlist: Playlist) {
        title.text = playlist.playlistName
        description.text = "${ playlist.trackAmount.toString() } треков"
        if(playlist.imageUri!=null)
            Glide.with(itemView)
                .load(playlist.imageUri)
                .placeholder(R.drawable.tracks_place_holder)
//                .transform(RoundedCorners(R.dimen.small_corner_radius))
                .into(picture)
    }
}