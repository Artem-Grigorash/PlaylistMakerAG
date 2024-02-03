package com.example.playlistmakerag.mediateka.ui.view_models

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import com.example.playlistmakerag.mediateka.domain.db.PlaylistInteractor
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistViewModel(private val interactor: PlaylistInteractor): AddPlaylistViewModel(interactor) {

}