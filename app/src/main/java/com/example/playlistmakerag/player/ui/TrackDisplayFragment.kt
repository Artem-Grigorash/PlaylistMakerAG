package com.example.playlistmakerag.player.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentTrackDisplayBinding
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.view_models.TrackState
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale

class TrackDisplayFragment: Fragment(), TrackView{

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
    private lateinit var binding: FragmentTrackDisplayBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrackDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        val lastTrack: Track =
            Gson().fromJson(intent?.getStringExtra("LAST_TRACK"), Track::class.java)

        url = lastTrack.previewUrl

        setInfo(lastTrack)


        arrayBack.setOnClickListener {
            finish()
        }

        play.isEnabled = true
        play.setImageResource(R.drawable.play)

        play.setOnClickListener {
            viewModel.onPlayClicked()
        }


        viewModel.getTrackState().observe(viewLifecycleOwner) { state ->
            render(state)
            buttonControl(state)
        }

        viewModel.getTime().observe(viewLifecycleOwner) { time ->
            updateTime(time)
        }
    }
    private fun updateTime(time: String) {
        progress.text = time
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