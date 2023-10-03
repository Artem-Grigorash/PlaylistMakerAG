package com.example.playlistmakerag.search.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentSearchBinding
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayActivity
import com.example.playlistmakerag.search.ui.view_models.SearchState
import com.example.playlistmakerag.search.ui.view_models.SearchViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputEditText = binding.searchEdit
        val textValue: String = inputEditText.text.toString()
        outState.putString(INPUT_TEXT, textValue)
    }


    private val tracks = ArrayList<Track>()
    private val recentTracks = ArrayList<Track>()
    private val adapter = TracksAdapter(tracks)
    private val recentAdapter = TracksAdapter(recentTracks)

    private lateinit var clearButton: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeholder: ImageView
    private lateinit var reloadButton: Button
    private lateinit var hisrory: LinearLayout
    private lateinit var historyRecycler: RecyclerView
    private lateinit var cleanHistoryButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var actualResponse: ArrayList<Track>

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actualResponse= ArrayList()

        viewModel.getSearchState().observe(viewLifecycleOwner) { state ->
            render(state)
        }


        viewModel.getSearchStateResponse().observe(viewLifecycleOwner) { res ->
            actualResponse = res
            viewModel.searchTracks(res, inputEditText.text.toString())
        }

        viewModel.getHistory().observe(viewLifecycleOwner) { history ->
            recentTracks.clear()

            for (track in history) {
                recentTracks.add(track)
            }

            recentAdapter.notifyDataSetChanged()
        }


        setViews()


        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            viewModel.reloadTracks()
            hisrory.visibility =
                if (hasFocus && inputEditText.text.isEmpty() && recentTracks.size != 0) View.VISIBLE else View.GONE
        }

        hisrory.visibility = View.GONE

        clearButton.setOnClickListener {
            clear()
        }

        adapter.itemClickListener = { _, track ->
            viewModel.addTrack(track, recentTracks)
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

        recentAdapter.itemClickListener = { _, track ->
            viewModel.addTrack(track, recentTracks)
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

        cleanHistoryButton.setOnClickListener {
            viewModel.clean()
            recentTracks.clear()
            recentAdapter.notifyDataSetChanged()
            hisrory.visibility = View.GONE
        }

        reloadButton.setOnClickListener {
            viewModel.onReloadClicked(inputEditText.text.toString())
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearButton.visibility = View.GONE
                hisrory.visibility =
                    if (inputEditText.hasFocus() && s?.isEmpty() == true && recentTracks.size != 0) View.VISIBLE else View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s.toString())
                clearButton.visibility = viewModel.clearButtonVisibility(s)
                recyclerView.visibility = if (s?.isEmpty() == true) View.GONE else View.VISIBLE
                progressBar.visibility = if (s?.isEmpty() == true) View.INVISIBLE else View.VISIBLE
                hisrory.visibility =
                    if (inputEditText.hasFocus() && s?.isEmpty() == true && recentTracks.size != 0) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                progressBar.visibility = if (s?.isEmpty() == true) View.INVISIBLE else View.VISIBLE
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

    }



    private fun clear() {
        inputEditText.setText("")
        tracks.clear()
        adapter.notifyDataSetChanged()
        recentAdapter.notifyDataSetChanged()
        placeholder.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        reloadButton.visibility = View.GONE
        reloadButton.isClickable = false
    }

    private fun openTrack(track: Track) {
        viewModel.onItemClicked(track)
        val trackJson = Gson().toJson(track)
        val intent = Intent(requireContext(), TrackDisplayActivity::class.java)
        intent.putExtra("LAST_TRACK", trackJson)
        startActivity(intent)
        recentAdapter.notifyDataSetChanged()
    }

    private fun render(state: SearchState) {
        when (state) {
            SearchState.BadConnection -> showBadConnection()
            SearchState.Data -> showData(actualResponse)
            SearchState.Loading -> showLoading()
            SearchState.NothingFound -> showNothingFound()

        }
    }

    private fun showBadConnection() {
        progressBar.visibility = View.GONE

        showMessage(
            getString(R.string.something_went_wrong),
            "",
            R.drawable.tracks_placeholder_ce
        )
        reloadButton.visibility = View.VISIBLE
        reloadButton.isClickable = true
    }

    private fun showData(response: ArrayList<Track>) {
        reloadButton.visibility = View.GONE
        placeholder.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        tracks.clear()
        tracks.addAll(response)
        adapter.notifyDataSetChanged()
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        reloadButton.visibility = View.GONE
        placeholder.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        reloadButton.isClickable = false
    }

    private fun showNothingFound() {
        progressBar.visibility = View.GONE
        tracks.clear()

        showMessage(
            getString(R.string.nothing_found),
            "",
            R.drawable.tracks_placeholder_nf
        )
    }

    private fun setViews() {
        recyclerView = binding.recyclerViewTracks
        inputEditText = binding.searchEdit
        clearButton = binding.clearIcon
        placeholderMessage = binding.placeholderMessage
        placeholder = binding.placeholderNF
        reloadButton = binding.reloadButton
        hisrory = binding.story
        historyRecycler = binding.searchHistoryRecycler
        cleanHistoryButton = binding.cleanHistoryButton
        progressBar = binding.progressBar

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        historyRecycler.adapter = recentAdapter
        historyRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun showMessage(text: String, additionalMessage: String, holderImage: Int) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            placeholder.visibility = View.VISIBLE
            tracks.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            placeholder.setImageResource(holderImage)
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(requireContext() , additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
            placeholder.visibility = View.GONE
        }
    }
}