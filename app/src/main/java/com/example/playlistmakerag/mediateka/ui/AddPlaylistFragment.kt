package com.example.playlistmakerag.mediateka.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import com.example.playlistmakerag.databinding.FragmentSearchBinding

class AddPlaylistFragment : Fragment() {

    private lateinit var binding: FragmentAddPlaylistBinding

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        saveButton.isClickable = false
        saveButton.isEnabled = false
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addPlaylistFragment_to_mediatekaFragment)
        }
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
            }
        }
        nameEditText.addTextChangedListener(textWatcher)
    }

    private fun setViews(){
        nameEditText = binding.editName
        descriptionEditText = binding.editDescription
        saveButton = binding.saveButton
        backButton = binding.backButton
    }
}