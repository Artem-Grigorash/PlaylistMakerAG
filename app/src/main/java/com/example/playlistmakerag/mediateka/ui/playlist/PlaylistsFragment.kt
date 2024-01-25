package com.example.playlistmakerag.mediateka.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentPlaylistsBinding
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.ui.PlaylistAdapter
import com.example.playlistmakerag.mediateka.ui.PlaylistInfoFragment
import com.example.playlistmakerag.mediateka.ui.PlaylistsState
import com.example.playlistmakerag.mediateka.ui.view_models.PlaylistsViewModel
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment: Fragment() {
    companion object {
        private const val NUMBER = "number"

        fun newInstance(number: Int) = PlaylistsFragment().apply {
            arguments = Bundle().apply {
                putInt(NUMBER, number)
            }
        }
    }

    private var adapter: PlaylistAdapter? = null
    private lateinit var recyclerView: RecyclerView

    private val viewModel by viewModel<PlaylistsViewModel>()


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

        adapter = PlaylistAdapter()
        recyclerView = binding.recyclerView

        viewModel.fillData()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(),  2)
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.restart).setOnClickListener {
            findNavController().navigate(R.id.action_mediatekaFragment_to_addPlaylistFragment)
        }

        adapter!!.itemClickListener = { _, playlist ->
            if (viewModel.clickDebounce()) {
                openPlaylist(playlist)
            }
        }

    }

    private fun openPlaylist(playlist: Playlist) {
        val playlistJson = Gson().toJson(playlist)
        findNavController().navigate(
            R.id.action_mediatekaFragment_to_playlistInfoFragment,
            PlaylistInfoFragment.createArgs(playlistJson)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        recyclerView.adapter = null
    }

    private fun render(state: PlaylistsState) {
        when (state) {
            is PlaylistsState.Content -> showContent(state.playlists)
            is PlaylistsState.Empty -> showEmpty()
            is PlaylistsState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.restart.visibility = View.VISIBLE
        binding.placeholder.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        recyclerView.visibility = View.GONE
        adapter?.notifyDataSetChanged()

    }

    private fun showEmpty() {
        binding.restart.visibility = View.VISIBLE
        binding.placeholder.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        adapter?.notifyDataSetChanged()

    }

    private fun showContent(movies: List<Playlist>) {
        binding.restart.visibility = View.VISIBLE
        binding.placeholder.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE

        adapter?.playlists?.clear()
        adapter?.playlists?.addAll(movies)
        adapter?.notifyDataSetChanged()
    }
}