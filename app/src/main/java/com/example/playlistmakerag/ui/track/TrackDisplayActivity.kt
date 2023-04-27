package com.example.playlistmakerag.ui.track

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.R
import com.example.playlistmakerag.data.dto.Track
import com.example.playlistmakerag.presentation.track.TrackPresenter
import com.example.playlistmakerag.presentation.track.TrackView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class TrackDisplayActivity : AppCompatActivity(), TrackView {

    private var mediaPlayer = MediaPlayer()
    private var handler: Handler? = null

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

    private lateinit var presenter : TrackPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_display)

        setViews()
        presenter = TrackPresenter(this)
        val lastTrack: Track = Gson().fromJson(intent?.getStringExtra("LAST_TRACK"), Track::class.java)
        handler = Handler(Looper.getMainLooper())

        presenter.setInfo(
            lastTrack,
            nameOfTrack,
            authorOfTrack,
            timeOfTrack,
            album,
            year,
            genre,
            country,
            trackPicture
        )

        arrayBack.setOnClickListener {
            presenter.onArrayBackClicked(this)
        }

        val url : String = lastTrack.previewUrl
        mediaPlayer.setDataSource(url)
        presenter.preparePlayer(mediaPlayer, play)

        play.setOnClickListener {
            presenter.onPlayClicked()
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

}