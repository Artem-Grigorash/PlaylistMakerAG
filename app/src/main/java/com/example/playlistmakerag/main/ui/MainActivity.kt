package com.example.playlistmakerag.main.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmakerag.R
import com.example.playlistmakerag.main.domain.MainViewModel
import com.example.playlistmakerag.player.domain.view_models.TrackViewModel
import com.example.playlistmakerag.search.ui.SearchActivity

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var searchButton: Button
    private lateinit var mediatekaButton: Button
    private lateinit var settingsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViews()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        mediatekaButton.setOnClickListener {
//            val intent = Intent(this, MediatekaActivity::class.java)
//            startActivity(intent)
        }
        settingsButton.setOnClickListener {
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
        }
        viewModel.getState().observe(this){}
    }
    private fun setViews(){
        searchButton = findViewById(R.id.search_button)
        mediatekaButton = findViewById(R.id.mediateka_button)
        settingsButton = findViewById(R.id.settings_button)
    }

}