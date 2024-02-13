package com.example.playlistmakerag.mediateka.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentAddPlaylistBinding
import com.example.playlistmakerag.mediateka.ui.view_models.AddPlaylistViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

open class AddPlaylistFragment : Fragment() {

    open lateinit var binding: FragmentAddPlaylistBinding

    open lateinit var nameEditText: EditText
    open lateinit var descriptionEditText: EditText
    open lateinit var saveButton: Button
    open lateinit var backButton: ImageView
    var flag = false
    lateinit var confirmDialog: MaterialAlertDialogBuilder

    open val viewModel by viewModel<AddPlaylistViewModel>()

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

        binding.smallName.visibility = View.GONE
        binding.smallDescription.visibility = View.GONE
        binding.nameShape.visibility = View.GONE
        binding.descriptionShape.visibility = View.GONE
        binding.nameShapeEnabled.visibility = View.VISIBLE
        binding.descriptionShapeEnabled.visibility = View.VISIBLE

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
                if (s?.isNotEmpty() == true) {
                    binding.smallName.visibility = View.VISIBLE
                    binding.nameShape.visibility = View.VISIBLE
                    binding.nameShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallName.visibility = View.GONE
                    binding.nameShape.visibility = View.GONE
                    binding.nameShapeEnabled.visibility = View.VISIBLE
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
                if (s?.isNotEmpty() == true) {
                    binding.smallName.visibility = View.VISIBLE
                    binding.nameShape.visibility = View.VISIBLE
                    binding.nameShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallName.visibility = View.GONE
                    binding.nameShape.visibility = View.GONE
                    binding.nameShapeEnabled.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton.isClickable = s?.isNotEmpty() == true
                saveButton.isEnabled = s?.isNotEmpty() == true
                if (s?.isNotEmpty() == true) {
                    binding.smallName.visibility = View.VISIBLE
                    binding.nameShape.visibility = View.VISIBLE
                    binding.nameShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallName.visibility = View.GONE
                    binding.nameShape.visibility = View.GONE
                    binding.nameShapeEnabled.visibility = View.VISIBLE
                }
            }
        }
        nameEditText.addTextChangedListener(textWatcher)

        val textWatcherDescription = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.smallDescription.visibility = View.VISIBLE
                    binding.descriptionShape.visibility = View.VISIBLE
                    binding.descriptionShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallDescription.visibility = View.GONE
                    binding.descriptionShape.visibility = View.GONE
                    binding.descriptionShapeEnabled.visibility = View.VISIBLE
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.smallDescription.visibility = View.VISIBLE
                    binding.descriptionShape.visibility = View.VISIBLE
                    binding.descriptionShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallDescription.visibility = View.GONE
                    binding.descriptionShape.visibility = View.GONE
                    binding.descriptionShapeEnabled.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    binding.smallDescription.visibility = View.VISIBLE
                    binding.descriptionShape.visibility = View.VISIBLE
                    binding.descriptionShapeEnabled.visibility = View.GONE
                }
                else{
                    binding.smallDescription.visibility = View.GONE
                    binding.descriptionShape.visibility = View.GONE
                    binding.descriptionShapeEnabled.visibility = View.VISIBLE
                }
            }
        }
        descriptionEditText.addTextChangedListener(textWatcherDescription)

        var actualUri: Uri? = null
        //регистрируем событие, которое вызывает photo picker
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
            actualUri=uri
                if (uri != null) {
                    flag=true
                    Glide.with(requireContext())
                        .load(uri)
                        .placeholder(R.drawable.tracks_place_holder)
                        .transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius)))
                        .into(binding.pickImage)
                    saveImageToPrivateStorage(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        //по нажатию на кнопку pickImage запускаем photo picker
        binding.pickImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.saveButton.setOnClickListener{
            save(actualUri)
        }

        confirmDialog = MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MyApp_Dialog_Alert)
            .setTitle("Завершить создание плейлиста?")
            .setNeutralButton("Отмена") { dialog, which ->
                // ничего не делаем
            }.setPositiveButton("Завершить") { dialog, which ->
                // сохраняем изменения и выходим
                findNavController().navigateUp()
            }

        binding.backButton.setOnClickListener {
            back()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

    open fun back(){
        if(nameEditText.text.isNotEmpty() || descriptionEditText.text.isNotEmpty() || flag)
            confirmDialog.show()
        else
            findNavController().navigateUp()
    }

    open fun save(actualUri: Uri?){
        viewModel.savePlaylist(nameEditText.text.toString(), descriptionEditText.text.toString(), actualUri)
        showMessage("Плейлист ${nameEditText.text} создан")
        findNavController().navigateUp()
    }

    fun showMessage(textInp: String) {
        val inflater = layoutInflater
        val container = requireActivity().findViewById<ViewGroup>(R.id.custom_toast_container)
        val layout: View = inflater.inflate(R.layout.custom_toast, container)
        val text: TextView = layout.findViewById(R.id.text)
        text.text = textInp
        with (Toast(requireContext())) {
            setGravity(Gravity.BOTTOM, 0, 0)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
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