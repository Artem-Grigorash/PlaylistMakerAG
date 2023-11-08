package com.example.playlistmakerag.mediateka.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmakerag.mediateka.ui.history.HistoryAdapter
import com.example.playlistmakerag.mediateka.ui.history.HistoryState
import com.example.playlistmakerag.mediateka.ui.view_models.FavouriteTracksViewModel
import com.example.playlistmakerag.mediateka.ui.view_models.HistoryViewModel
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class FavoriteTracksFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTracksBinding
    private val viewModel by viewModel<HistoryViewModel>()
    private var adapter: HistoryAdapter? = null

    private lateinit var placeholderMessage: TextView
    private lateinit var historyList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var placeholderImage: ImageView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter()
        placeholderMessage = binding.placeholderMessage
        placeholderImage = binding.placeholder
        historyList = binding.historyList
        progressBar = binding.progressBar3
        placeholderImage.setImageResource(R.drawable.tracks_placeholder_nf)
        historyList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        historyList.adapter = adapter

        viewModel.fillData()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        adapter!!.itemClickListener = { _, track ->
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

    }

    private fun openTrack(track: Track) {
        val trackJson = Gson().toJson(track)
//        val intent = Intent(requireContext(), TrackDisplayActivity::class.java)
//        intent.putExtra("LAST_TRACK", trackJson)
//        startActivity(intent)
        findNavController().navigate(
            R.id.action_mediatekaFragment_to_trackDisplayFragment2,
            TrackDisplayFragment.createArgs(trackJson)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        historyList.adapter = null
    }

    private fun render(state: HistoryState) {
        when (state) {
            is HistoryState.Content -> showContent(state.movies)
            is HistoryState.Empty -> showEmpty(state.message)
            is HistoryState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        historyList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        placeholderImage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showEmpty(message: String) {
        historyList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        placeholderImage.visibility = View.VISIBLE
        placeholderMessage.text = message
    }

    private fun showContent(tracks: List<Track>) {
        historyList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        placeholderImage.visibility = View.GONE
        adapter?.tracks?.clear()
        adapter?.tracks?.addAll(tracks.reversed())
        adapter?.notifyDataSetChanged()
    }

    companion object {
        private const val NUMBER = "number"

        fun newInstance(number: Int) = FavoriteTracksFragment().apply {
            arguments = Bundle().apply {
                putInt(NUMBER, number)
            }
        }
    }
}