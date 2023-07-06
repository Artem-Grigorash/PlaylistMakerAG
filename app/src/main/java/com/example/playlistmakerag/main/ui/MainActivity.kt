package com.example.playlistmakerag.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.playlistmakerag.MediatekaActivity
import com.example.playlistmakerag.R
import com.example.playlistmakerag.search.ui.SearchActivity
import com.example.playlistmakerag.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val mediatekaButton = findViewById<Button>(R.id.mediateka_button)
        val intent = Intent(this, MediatekaActivity::class.java)
        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(intent)
            }
        }
        mediatekaButton.setOnClickListener(imageClickListener)

        val settingsButton = findViewById<Button>(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)        }
    }
}