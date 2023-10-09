package com.example.playlistmakerag.mediateka.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import com.example.playlistmakerag.databinding.FragmentSearchBinding

class AddPlaylistFragment : Fragment() {

    private lateinit var binding: FragmentAddPlaylistBinding

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_playlist, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.isClickable = s.isNullOrEmpty()
//                saveButton.background = if (s?.isEmpty() == true) R.color.background_main else R.color.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveButton.isClickable = s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton.isClickable = s.isNullOrEmpty()
            }
        }
        nameEditText.addTextChangedListener(textWatcher)
        descriptionEditText.addTextChangedListener(textWatcher)
    }

    private fun setViews(){
        nameEditText = binding.editName
        descriptionEditText = binding.editDescription
        saveButton=binding.saveButton
    }
}