package com.example.playlistmakerag.mediateka.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import java.io.File
import java.io.FileOutputStream

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


        //регистрируем событие, которое вызывает photo picker
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
                if (uri != null) {
                    binding.pickImage.setImageURI(uri)
                    saveImageToPrivateStorage(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        //по нажатию на кнопку pickImage запускаем photo picker
        binding.pickImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

    private fun saveImageToPrivateStorage(uri: Uri) {
        //создаём экземпляр класса File, который указывает на нужный каталог
        val filePath = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        //создаем каталог, если он не создан
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        //создаём экземпляр класса File, который указывает на файл внутри каталога
        val file = File(filePath, "first_cover.jpg")
        // создаём входящий поток байтов из выбранной картинки
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        // создаём исходящий поток байтов в созданный выше файл
        val outputStream = FileOutputStream(file)
        // записываем картинку с помощью BitmapFactory
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }


    private fun setViews(){
        nameEditText = binding.editName
        descriptionEditText = binding.editDescription
        saveButton = binding.saveButton
        backButton = binding.backButton
    }
}