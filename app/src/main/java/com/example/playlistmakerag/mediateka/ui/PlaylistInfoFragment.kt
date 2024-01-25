package com.example.playlistmakerag.mediateka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayFragment
import com.google.gson.Gson


class PlaylistInfoFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaylistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var time: TextView
    private lateinit var tracklist: RecyclerView
    private lateinit var placeholderImage: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val link = requireArguments().getString(PlaylistInfoFragment.ARGS_PLAYLIST)
        val lastPlaylist: Playlist = Gson().fromJson(link, Playlist::class.java)

        name=binding.name
        description=binding.descriptionText
        time=binding.timeText
        tracklist=binding.tracklist
        placeholderImage=binding.placeholderImage

        name.text=lastPlaylist.playlistName
        description.text=lastPlaylist.playlistDescription
    }

    companion object {
        const val ARGS_PLAYLIST = "playlist"

        fun createArgs(playlist: String): Bundle =
            bundleOf(ARGS_PLAYLIST to playlist)
    }

}