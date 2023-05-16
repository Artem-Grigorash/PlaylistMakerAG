package com.example.playlistmakerag.ui.track

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.R
import com.example.playlistmakerag.creator.Creator
import com.example.playlistmakerag.data.dto.Track
import com.example.playlistmakerag.data.glide.GlideCreator
import com.example.playlistmakerag.presentation.track.TrackPresenter
import com.example.playlistmakerag.presentation.track.TrackView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TrackDisplayActivity : AppCompatActivity(), TrackView {

    private lateinit var presenter : TrackPresenter

//    private var mediaPlayer = MediaPlayer()
    private val glide = GlideCreator()
    private lateinit var handler: Handler

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
        handler = Handler(Looper.getMainLooper())

        setInfo(lastTrack)

        arrayBack.setOnClickListener {
            presenter.onArrayBackClicked(this)
        }

        val url : String = lastTrack.previewUrl
        presenter = Creator.providePresenter(url)
        presenter.preparePlayer(play)

        play.setOnClickListener {
            presenter.onPlayClicked(play,progress)
        }

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

    override fun onPause() {
        super.onPause()
        presenter.pausePlayer(play)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.
    }



}