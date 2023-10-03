package com.example.playlistmakerag.player.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.view_models.TrackState
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class TrackDisplayActivity : AppCompatActivity(), TrackView {

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
    private lateinit var like : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_track_display)

        setViews()
        val lastTrack: Track =
            Gson().fromJson(intent?.getStringExtra("LAST_TRACK"), Track::class.java)


        url = lastTrack.previewUrl
        setInfo(lastTrack)

        lastTrack.isFavorite=viewModel.checkIsFavorite(lastTrack)

        viewModel.getTrack(lastTrack)

        arrayBack.setOnClickListener {
            finish()
        }
        play.isEnabled = true
        play.setImageResource(R.drawable.play)

        play.setOnClickListener {
            viewModel.onPlayClicked()
        }

        like.setOnClickListener {
            viewModel.onLikeClicked()
        }

        viewModel.getTrackState().observe(this) { state ->
            render(state)
            buttonControl(state)
        }

        viewModel.getTime().observe(this) { time ->
            updateTime(time)
        }

        viewModel.getIsFavorite().observe(this) {isFavorite ->
            updateLikeButton(isFavorite)
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
        arrayBack = findViewById(R.id.arrayBack)
        trackPicture = findViewById(R.id.trackPicture)
        nameOfTrack = findViewById(R.id.name_of_track)
        authorOfTrack = findViewById(R.id.author_of_track)
        timeOfTrack = findViewById(R.id.time_of_track_value)
        album = findViewById(R.id.album_value)
        year = findViewById(R.id.year_value)
        genre = findViewById(R.id.genre_value)
        country = findViewById(R.id.country_value)
        play = findViewById(R.id.play_button)
        progress = findViewById(R.id.time)
        like = findViewById(R.id.like_button)
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