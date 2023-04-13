package com.example.playlistmakerag

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TrackDisplayActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_display)

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


        val lastTrack: Track = Gson().fromJson(intent?.getStringExtra("LAST_TRACK"), Track::class.java)

        nameOfTrack.text = lastTrack.trackName
        authorOfTrack.text = lastTrack.artistName
        val time = SimpleDateFormat("mm:ss", Locale.getDefault()).format(lastTrack.trackTimeMillis)
        timeOfTrack.text = time
        if (lastTrack.collectionName!=null)
            album.text = lastTrack.collectionName
        else
            album.visibility = View.GONE
        year.text = lastTrack.releaseDate.substring(0,4)
        genre.text = lastTrack.primaryGenreName
        country.text = lastTrack.country

        Glide.with(trackPicture)
            .load(lastTrack.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.tracks_place_holder)
            .transform(RoundedCorners(30))
            .into(trackPicture)

        arrayBack.setOnClickListener {
            finish()
        }

        val url : String = lastTrack.previewUrl
        mediaPlayer.setDataSource(url)

        preparePlayer()

        play.setOnClickListener {
            playbackControl()
        }
    }

    private fun preparePlayer() {
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            //play
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
//        play.text = "PAUSE"
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
//        play.text = "PLAY"
        playerState = STATE_PAUSED
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
}