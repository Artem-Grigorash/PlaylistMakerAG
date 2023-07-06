package com.example.playlistmakerag.player.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.playlistmakerag.player.domain.TrackState
import com.example.playlistmakerag.player.domain.TrackViewModel

class TrackActivity : ComponentActivity() {
    private val viewModel by viewModels<TrackViewModel> { TrackViewModel.getViewModelFactory("123") }
    private lateinit var binding: ActivityTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getScreenStateLiveData().observe(this) { screenState ->
            // 1
            when (screenState) {
                is TrackState.Content -> {
                    changeContentVisibility(loading = false)
                    binding.picture.setImage(screenState.trackModel.pictureUrl)
                    binding.author.text = screenState.trackModel.author
                    binding.trackName.text = screenState.trackModel.name
                }
                is TrackState.Loading -> {
                    changeContentVisibility(loading = true)
                }
            }
        }
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.picture.isVisible = !loading
        binding.author.isVisible = !loading
        binding.trackName.isVisible = !loading
    }
}