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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_track_display)




}