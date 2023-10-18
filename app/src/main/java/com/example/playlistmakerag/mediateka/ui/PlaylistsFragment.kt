package com.example.playlistmakerag.mediateka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentPlaylistsBinding
import com.example.playlistmakerag.mediateka.ui.view_models.PlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlaylistsFragment: Fragment() {
    companion object {
        private const val NUMBER = "number"

        fun newInstance(number: Int) = PlaylistsFragment().apply {
            arguments = Bundle().apply {
                putInt(NUMBER, number)
            }
        }
    }

    private val playlistsViewModel: PlaylistsViewModel by viewModel {
        parametersOf()
    }

    private lateinit var binding: FragmentPlaylistsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.placeholderMessage.text = getString(R.string.empty_playlist)
        binding.placeholder.setImageResource(R.drawable.tracks_placeholder_nf)
        binding.restart.visibility = View.VISIBLE
        binding.restart.text = getString(R.string.new_playlist)

        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(requireContext(),  2)
        recyclerView.adapter = PlaylistAdapter()

        view.findViewById<Button>(R.id.restart).setOnClickListener {
            findNavController().navigate(R.id.action_mediatekaFragment_to_addPlaylistFragment)
        }

    }
}