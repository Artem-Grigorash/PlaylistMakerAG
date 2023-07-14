package com.example.playlistmakerag.main.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakerag.mediateka.MediatekaActivity
import com.example.playlistmakerag.R
import com.example.playlistmakerag.search.ui.SearchActivity
import com.example.playlistmakerag.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var mediatekaButton: Button
    private lateinit var settingsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViews()



        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        mediatekaButton.setOnClickListener {
            val intent = Intent(this, MediatekaActivity::class.java)
            startActivity(intent)
        }
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setViews(){
        searchButton = findViewById(R.id.search_button)
        mediatekaButton = findViewById(R.id.mediateka_button)
        settingsButton = findViewById(R.id.settings_button)
    }

}