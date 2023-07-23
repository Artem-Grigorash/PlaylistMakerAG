package com.example.playlistmakerag.player.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmakerag.player.data.glide.GlideCreator
import com.example.playlistmakerag.R
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.view_models.TrackState
import com.example.playlistmakerag.player.ui.view_models.TrackViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TrackDisplayActivity : ComponentActivity(), TrackView {
private lateinit var viewModel: TrackViewModel

    private val glide = GlideCreator()

    private lateinit var arrayBack : ImageView
    private lateinit var trackPicture: ImageView
    private lateinit var nameOfTrack: TextView
    private lateinit var authorOfTrack: TextView
    private lateinit var timeOfTrack : TextView
    private lateinit var album : TextView
    private lateinit var year : TextView
    private lateinit var genre : TextView
    private lateinit var country : TextView
    private lateinit var play : FloatingActionButton
    private lateinit var progress : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_display)

        setViews()
        val lastTrack: Track = Gson().fromJson(intent?.getStringExtra("LAST_TRACK"), Track::class.java)

        setInfo(lastTrack)

        arrayBack.setOnClickListener {
            finish()
        }

        val url : String = lastTrack.previewUrl
        viewModel = ViewModelProvider(this, TrackViewModel.getViewModelFactory(url))[TrackViewModel::class.java]

        play.isEnabled = true
        play.setImageResource(R.drawable.play)

        play.setOnClickListener {
            viewModel.onPlayClicked()
        }


        viewModel.getTrackState().observe(this){ state->
            render(state)
            buttonControl(state)
        }

        viewModel.getTime().observe(this){time ->
            updateTime(time)
        }
    }

    private fun updateTime(time:String){
        progress.text = time
    }

    private fun buttonControl(state: TrackState){
        if (state == TrackState.Pause)
            play.setImageResource(R.drawable.pause)
        else
            play.setImageResource(R.drawable.play)
    }

    private fun setViews(){
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
    }
    private fun setInfo(
        track: Track,
    ){
        nameOfTrack.text = track.trackName
        authorOfTrack.text = track.artistName
        val timer = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        timeOfTrack.text = timer
        album.text = track.collectionName
        year.text = track.releaseDate.substring(0,4)
        genre.text = track.primaryGenreName
        country.text = track.country
        glide.setTrackPicture(trackPicture, track)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.delete()
    }

    override fun render(state: TrackState) {
        when (state){
            is TrackState.Pause -> showPaused()
            is TrackState.Play -> showPlayed()
        }
    }

    private fun showPlayed(){
        viewModel.startPlayer()
    }
    private fun showPaused(){
        viewModel.pausePlayer()
    }
}