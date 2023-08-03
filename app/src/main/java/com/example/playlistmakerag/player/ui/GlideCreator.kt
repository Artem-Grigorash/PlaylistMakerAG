package com.example.playlistmakerag.player.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.models.Track

class GlideCreator {
    fun setTrackPicture(picture: ImageView, track: Track) {
        Glide.with(picture)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.tracks_place_holder)
            .transform(RoundedCorners(30))
            .into(picture)
    }
}