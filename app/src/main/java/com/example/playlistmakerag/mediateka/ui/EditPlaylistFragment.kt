package com.example.playlistmakerag.mediateka.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.ui.view_models.EditPlaylistViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistFragment: AddPlaylistFragment() {
    override lateinit var binding: FragmentAddPlaylistBinding

    override val viewModel by viewModel<EditPlaylistViewModel>()

    private val link = requireArguments().getString(ARGS_PLAYLIST)
    private val lastPlaylist: Playlist = Gson().fromJson(link, Playlist::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameEditText.setText(lastPlaylist.playlistName)
        descriptionEditText.setText(lastPlaylist.playlistDescription)
        if(lastPlaylist.imageUri!=null){
            Glide.with(requireActivity())
                .load(lastPlaylist.imageUri)
                .placeholder(R.drawable.tracks_place_holder)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.pickImage)
        }

        binding.header.text = "Редактировать"
        binding.saveButton.text = "Сохранить"

    }

    override fun back() {
        findNavController().navigateUp()
    }

    override fun save(actualUri: Uri?) {
        findNavController().navigateUp()
        if (nameEditText.text.toString() != lastPlaylist.playlistName || descriptionEditText.text.toString() != lastPlaylist.playlistDescription || actualUri.toString()!=lastPlaylist.imageUri) {
            viewModel.updatePlaylist(lastPlaylist, nameEditText.text.toString(), descriptionEditText.text.toString(), actualUri.toString())
        }
    }

    companion object {
        const val ARGS_PLAYLIST = "playlist"
        fun createArgs(playlist: String): Bundle =
            bundleOf(ARGS_PLAYLIST to playlist)
    }
}