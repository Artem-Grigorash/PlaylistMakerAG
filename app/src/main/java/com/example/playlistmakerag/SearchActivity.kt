package com.example.playlistmakerag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class SearchActivity : AppCompatActivity() {

    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputEditText = findViewById<EditText>(R.id.searchEdit)
        val textValue: String = inputEditText.text.toString()
        outState.putString(INPUT_TEXT,textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputEditText = findViewById<EditText>(R.id.searchEdit)
        val textValue = savedInstanceState.getString(INPUT_TEXT,"")
        inputEditText.setText(textValue)
    }



    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ItunesApi::class.java)
    private val tracks = ArrayList<Track>()
    private val adapter = TracksAdapter(tracks)

    private lateinit var clearButton: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeholder: ImageView
    private lateinit var reloadButton: Button
    private lateinit var searchBack: ImageView
    private lateinit var hisrory: LinearLayout





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recyclerViewTracks)
        inputEditText = findViewById(R.id.searchEdit)
        clearButton = findViewById(R.id.clearIcon)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        placeholder = findViewById(R.id.placeholderNF)
        reloadButton = findViewById(R.id.reload_button)
        searchBack = findViewById(R.id.search_back)
        hisrory = findViewById(R.id.story)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTracks()
            }
            false
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            hisrory.visibility = if(hasFocus && inputEditText.text.isEmpty()) View.VISIBLE else View.GONE
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            tracks.clear()
            adapter.notifyDataSetChanged()
            placeholder.visibility = View.GONE
            placeholderMessage.visibility = View.GONE
            reloadButton.visibility = View.GONE
            reloadButton.isClickable = false
        }

        reloadButton.setOnClickListener{
            searchTracks()
        }

        searchBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        adapter.itemClickListener = { position, track ->
            Toast.makeText(this, "track ${track.trackId}", Toast.LENGTH_SHORT).show()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearButton.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                hisrory.visibility = if(inputEditText.hasFocus() && s?.isEmpty() == true) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

    }


    //functions:

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
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


    fun searchTracks(){
        reloadButton.visibility = View.GONE
        placeholder.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        reloadButton.isClickable = false
        if (inputEditText.text.isNotEmpty()) {
            trackService.search(inputEditText.text.toString()).enqueue(object :
                Callback<TrackResponse> {
                override fun onResponse(call: Call<TrackResponse>,
                                        response: Response<TrackResponse>
                ) {

                    if (response.code() == 200) {
                        tracks.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            tracks.addAll(response.body()?.results!!)
                            adapter.notifyDataSetChanged()
                        }
                        if (tracks.isEmpty()) {
                            showMessage(getString(R.string.nothing_found), "", R.drawable.tracks_placeholder_nf)
                        }
                    }

                    else {
                        showMessage(getString(R.string.something_went_wrong), response.code().toString(), R.drawable.tracks_placeholder_ce)
                        reloadButton.visibility = View.VISIBLE
                        reloadButton.isClickable = true
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    showMessage(getString(R.string.something_went_wrong), t.message.toString(), R.drawable.tracks_placeholder_ce)
                    reloadButton.visibility = View.VISIBLE
                    reloadButton.isClickable = true
                }

            })
        }
    }


}