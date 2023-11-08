package com.example.playlistmakerag.player.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentTrackDisplayBinding
import com.example.playlistmakerag.mediateka.ui.PlaylistOnTrackAdapter
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.view_models.TrackState
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class TrackDisplayFragment : Fragment(), TrackView {

    private lateinit var url: String

    private val viewModel: TrackViewModel by viewModel {
        parametersOf(url)
    }

    private val glide = GlideCreator()

    private lateinit var arrayBack: ImageView
    private lateinit var trackPicture: ImageView
    private lateinit var nameOfTrack: TextView
    private lateinit var authorOfTrack: TextView
    private lateinit var timeOfTrack: TextView
    private lateinit var album: TextView
    private lateinit var year: TextView
    private lateinit var genre: TextView
    private lateinit var country: TextView
    private lateinit var play: FloatingActionButton
    private lateinit var progress: TextView
    private lateinit var like : ImageView
    private lateinit var addTrackButton: FloatingActionButton
    private lateinit var mainPart:  ConstraintLayout
    private lateinit var overlay: View
    private lateinit var newPlaylist: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistOnTrackAdapter

    private lateinit var binding: FragmentTrackDisplayBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrackDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARGS_SONG = "song"

        fun createArgs(song: String): Bundle =
            bundleOf(ARGS_SONG to song)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()

        val song = requireArguments().getString(ARGS_SONG)
        val lastTrack: Track = Gson().fromJson(song, Track::class.java)

        url = lastTrack.previewUrl
        setInfo(lastTrack)
        viewModel.fillData()
        viewModel.getTrack(lastTrack)

        arrayBack.setOnClickListener {
            findNavController().navigateUp()
        }
        play.isEnabled = true
        play.setImageResource(R.drawable.play)

        play.setOnClickListener {
            viewModel.onPlayClicked()
        }

        like.setOnClickListener {
            viewModel.onLikeClicked()
        }

        viewModel.getTrackState().observe(viewLifecycleOwner) { state ->
            render(state)
            buttonControl(state)
        }

        viewModel.getTime().observe(viewLifecycleOwner) { time ->
            updateTime(time)
        }

        viewModel.getIsFavorite().observe(viewLifecycleOwner) {isFavorite ->
            updateLikeButton(isFavorite)
        }

        newPlaylist.setOnClickListener {
            if (savedInstanceState == null) {
                it.findNavController()
                    .navigate(R.id.action_trackDisplayActivity_to_addPlaylistFragment)
                viewModel.onPlayClicked()
            }
        }

        val bottomSheetContainer = binding.standardBottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        addTrackButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        overlay.visibility = View.GONE
                    }
                    else -> {
                        overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        adapter = PlaylistOnTrackAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) {
            recyclerView.visibility = View.VISIBLE
            adapter.playlists.clear()
            adapter.playlists.addAll(it)
            adapter.notifyDataSetChanged()
        }


    }




    private fun updateTime(time: String) {
        progress.text = time
    }

    private fun updateLikeButton(isFavorite: Boolean){
        if (isFavorite)
            like.setImageResource(R.drawable.liked)
        else
            like.setImageResource(R.drawable.like)
    }

    private fun buttonControl(state: TrackState) {
        if (state == TrackState.Pause)
            play.setImageResource(R.drawable.play)
        else
            play.setImageResource(R.drawable.pause)
    }

    private fun setViews() {
        arrayBack = binding.arrayBack
        trackPicture = binding.trackPicture
        nameOfTrack = binding.nameOfTrack
        authorOfTrack = binding.authorOfTrack
        timeOfTrack = binding.timeOfTrackValue
        album = binding.albumValue
        year = binding.yearValue
        genre = binding.genreValue
        country = binding.countryValue
        play = binding.playButton
        progress = binding.time
        like = binding.likeButton
        addTrackButton = binding.addTrackButton
        mainPart = binding.mainPart
        overlay = binding.overlay
        newPlaylist = binding.newPlaylist
        recyclerView = binding.historyList
    }

    private fun setInfo(
        track: Track,
    ) {
        nameOfTrack.text = short(track.trackName)
        authorOfTrack.text = short(track.artistName)
        val timer = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        timeOfTrack.text = timer
        album.text = short(track.collectionName)
        year.text = track.releaseDate.substring(0, 4)
        genre.text = short(track.primaryGenreName)
        country.text = short(track.country)
        glide.setTrackPicture(trackPicture, track)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.delete()
    }

    override fun render(state: TrackState) {
        when (state) {
            is TrackState.Pause -> showPaused()
            is TrackState.Play -> showPlayed()
        }
    }

    private fun short(s: String): String {
        return if (s.length <= 27)
            s
        else
            s.substring(0, 24) + "..."
    }

    private fun showPlayed() {
        viewModel.startPlayer()
    }

    private fun showPaused() {
        viewModel.pausePlayer()
    }
}