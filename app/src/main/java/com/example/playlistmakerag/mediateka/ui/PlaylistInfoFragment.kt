package com.example.playlistmakerag.mediateka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmakerag.mediateka.domain.models.Playlist
import com.example.playlistmakerag.mediateka.ui.view_models.HistoryViewModel
import com.example.playlistmakerag.mediateka.ui.view_models.PlaylistInfoViewModel
import com.example.playlistmakerag.player.domain.models.Track
import com.example.playlistmakerag.player.ui.TrackDisplayFragment
import com.example.playlistmakerag.search.ui.TracksAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Locale


class PlaylistInfoFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistInfoBinding
    private val viewModel by viewModel<PlaylistInfoViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaylistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var timeAndAmount: TextView
    private lateinit var tracklist: RecyclerView
    private lateinit var placeholderImage: ImageView
    private lateinit var arrayBack: ImageView
    private lateinit var share: ImageView
    private lateinit var title: TextView
    private lateinit var descriptionAlbum: TextView
    private lateinit var placeholderAlbum: ImageView
    private lateinit var menu: ImageView
    private lateinit var overlay: View
    private lateinit var shareOption: TextView
    private lateinit var deleteOption: TextView

    private var tracks_for_adapter = ArrayList<Track>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val link = requireArguments().getString(PlaylistInfoFragment.ARGS_PLAYLIST)
        val lastPlaylist: Playlist = Gson().fromJson(link, Playlist::class.java)

        name=binding.name
        description=binding.descriptionText
        timeAndAmount=binding.timeText
        tracklist=binding.tracklist
        placeholderImage=binding.placeholderImage
        arrayBack = binding.arrayBack
        name.text=lastPlaylist.playlistName
        description.text=lastPlaylist.playlistDescription
        share = binding.share
        title = binding.title
        descriptionAlbum = binding.description
        placeholderAlbum = binding.placeholderAlbum
        menu = binding.menu
        overlay = binding.overlay
        shareOption = binding.shareOption
        deleteOption = binding.deleteOption

        val counterTracks = lastPlaylist.trackAmount
        var sec = 0L
        val listType: Type = object : TypeToken<ArrayList<Track?>?>() {}.type
        val tracks: ArrayList<Track>? = Gson().fromJson(lastPlaylist.addedTracks, listType)
        if (tracks != null) {
            for (t in tracks)
                sec+=t.trackTimeMillis
        }
        val time = SimpleDateFormat("mm", Locale.getDefault()).format(sec)
        timeAndAmount.text = "$time минут • $counterTracks треков"

        title.text = lastPlaylist.playlistName
        descriptionAlbum.text = "$counterTracks треков"

        if(lastPlaylist.imageUri!=null){
            Glide.with(requireActivity())
                .load(lastPlaylist.imageUri)
                .placeholder(R.drawable.placeholder_bordered)
                .transform(CenterCrop())
                .into(placeholderImage)
        }

        if(lastPlaylist.imageUri!=null){
            Glide.with(requireActivity())
                .load(lastPlaylist.imageUri)
                .placeholder(R.drawable.tracks_place_holder)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(placeholderAlbum)
        }

        arrayBack.setOnClickListener {
            findNavController().navigateUp()
        }

        if (tracks!=null)
            tracks_for_adapter = tracks
        tracklist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var adapter = TracksAdapter(tracks_for_adapter)
        tracklist.adapter=adapter

        adapter.itemClickListener = { _, track ->
            if (viewModel.clickDebounce()) {
                openTrack(track)
            }
        }

        adapter.itemLongClickListener = {_, track ->
            val confirmDialog: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MyApp_Dialog_Alert)
                .setTitle("Хотите удалить трек?")
                .setNegativeButton("НЕТ") { dialog, which ->
                    // ничего не делаем
                }.setPositiveButton("ДА") { dialog, which ->
                    viewModel.deleteTrack(lastPlaylist, track)
                    tracks_for_adapter.remove(track)
                    adapter = TracksAdapter(tracks_for_adapter)
                    tracklist.adapter=adapter
                    adapter.notifyDataSetChanged()
                }
            confirmDialog.show()
        }

        deleteOption.setOnClickListener{
            val confirmDialog: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MyApp_Dialog_Alert)
                .setTitle("Хотите удалить плейлист ${lastPlaylist.playlistName}?")
                .setNegativeButton("НЕТ") { dialog, which ->
                    // ничего не делаем
                }.setPositiveButton("ДА") { dialog, which ->
                    viewModel.deletePlaylist(lastPlaylist)
                    findNavController().navigateUp()
                }
            confirmDialog.show()
        }

        share.setOnClickListener {
            if (tracks_for_adapter.isEmpty()) {
                val toast = Toast.makeText(requireContext(), "В этом плейлисте нет списка треков, которым можно поделиться", Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                var str = "${lastPlaylist.playlistName}\n${lastPlaylist.playlistDescription}\n${lastPlaylist.trackAmount} треков\n"
                var c = 1
                for (tr in tracks_for_adapter){
                    str += "$c. ${tr.artistName} - ${tr.trackName} (${SimpleDateFormat("mm:ss", Locale.getDefault()).format(tr.trackTimeMillis)})\n"
                    c++
                }
                val shareIntent = viewModel.shareApp(str)
                startActivity(shareIntent)
            }
        }

        shareOption.setOnClickListener {
            if (tracks_for_adapter.isEmpty()) {
                val toast = Toast.makeText(requireContext(), "В этом плейлисте нет списка треков, которым можно поделиться", Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                var str = "${lastPlaylist.playlistName}\n${lastPlaylist.playlistDescription}\n${lastPlaylist.trackAmount} треков\n"
                var c = 1
                for (tr in tracks_for_adapter){
                    str += "$c. ${tr.artistName} - ${tr.trackName} (${SimpleDateFormat("mm:ss", Locale.getDefault()).format(tr.trackTimeMillis)})\n"
                    c++
                }
                val shareIntent = viewModel.shareApp(str)
                startActivity(shareIntent)
            }
        }

        val bottomSheetContainer = binding.menuBottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        menu.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        overlay.visibility = View.GONE
                    }

                    else -> {
                        overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    private fun openTrack(track: Track) {
        val trackJson = Gson().toJson(track)
        findNavController().navigate(
            R.id.action_playlistInfoFragment_to_trackDisplayFragment,
            TrackDisplayFragment.createArgs(trackJson)
        )
    }

    companion object {
        const val ARGS_PLAYLIST = "playlist"

        fun createArgs(playlist: String): Bundle =
            bundleOf(ARGS_PLAYLIST to playlist)
    }

}