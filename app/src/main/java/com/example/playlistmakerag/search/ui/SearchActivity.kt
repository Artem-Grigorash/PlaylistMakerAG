package com.example.playlistmakerag.search.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakerag.search.data.HISTORY_KEY
import com.example.playlistmakerag.R
import com.example.playlistmakerag.creator.Creator
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayActivity
import com.example.playlistmakerag.player.data.dto.TrackResponse
import com.example.playlistmakerag.search.ui.view_models.SearchState
import com.example.playlistmakerag.search.ui.view_models.SearchViewModel
import com.google.gson.Gson


class SearchActivity : AppCompatActivity() {

    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputEditText = findViewById<EditText>(R.id.searchEdit)
        val textValue: String = inputEditText.text.toString()
        outState.putString(INPUT_TEXT, textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputEditText = findViewById<EditText>(R.id.searchEdit)
        val textValue = savedInstanceState.getString(INPUT_TEXT, "")
        inputEditText.setText(textValue)
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
    private lateinit var searchBack: ImageView
    private lateinit var hisrory: LinearLayout
    private lateinit var historyRecycler: RecyclerView
    private lateinit var cleanHistoryButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: SearchViewModel
    private lateinit var sharedPref: SharedPreferences

    private lateinit var actualResponse: TrackResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        Creator.init(application)

        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getSearchViewModelFactory()
        )[SearchViewModel::class.java]

        viewModel.getSearchState().observe(this) { state ->
            render(state)
        }


        viewModel.getSearchStateResponse().observe(this) { res ->
            //здесь лежит актуальный response
            actualResponse = res

            viewModel.searchTracks(res, inputEditText.text.toString(), tracks)
        }

        viewModel.getHistory().observe(this){history ->
            recentTracks.clear()
            for (track in history)
                recentTracks.add(track)
            recentAdapter.notifyDataSetChanged()
        }


        setViews()

        sharedPref = viewModel.provideSharedPreferences(applicationContext)

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            viewModel.reloadTracks(sharedPref)
            hisrory.visibility =
                if (hasFocus && inputEditText.text.isEmpty() && recentTracks.size != 0) View.VISIBLE else View.GONE
        }

        hisrory.visibility = View.GONE

        clearButton.setOnClickListener {
            clear()
        }

        adapter.itemClickListener = { _, track ->
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

        recentAdapter.itemClickListener = { _, track ->
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

        sharedPref.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == HISTORY_KEY) {
                viewModel.reloadTracks(sharedPref)
            }
        }

        cleanHistoryButton.setOnClickListener {
            viewModel.clean(sharedPref)
            recentTracks.clear()
            recentAdapter.notifyDataSetChanged()
            hisrory.visibility = View.GONE
        }

        reloadButton.setOnClickListener {
            viewModel.onReloadClicked(inputEditText.text.toString())
        }

        searchBack.setOnClickListener {
            finish()
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
        viewModel.onItemClicked(track, sharedPref)
        val trackJson = Gson().toJson(track)
        val intent = Intent(this, TrackDisplayActivity::class.java)
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

    private fun showData(response: TrackResponse) {
        reloadButton.visibility = View.GONE
        placeholder.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        tracks.clear()
        tracks.addAll(response.results!!)
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
        recyclerView = findViewById(R.id.recyclerViewTracks)
        inputEditText = findViewById(R.id.searchEdit)
        clearButton = findViewById(R.id.clearIcon)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        placeholder = findViewById(R.id.placeholderNF)
        reloadButton = findViewById(R.id.reload_button)
        searchBack = findViewById(R.id.search_back)
        hisrory = findViewById(R.id.story)
        historyRecycler = findViewById(R.id.search_history_recycler)
        cleanHistoryButton = findViewById(R.id.clean_history_button)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        historyRecycler.adapter = recentAdapter
        historyRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
            placeholder.visibility = View.GONE
        }
    }
}

