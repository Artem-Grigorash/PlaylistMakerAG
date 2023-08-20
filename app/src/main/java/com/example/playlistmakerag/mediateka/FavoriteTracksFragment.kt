package com.example.playlistmakerag.mediateka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmakerag.mediateka.view_models.FavouriteTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class FavoriteTracksFragment : Fragment() {

    companion object {
        private const val NUMBER = "number"

        fun newInstance(number: Int) = FavoriteTracksFragment().apply {
            arguments = Bundle().apply {
                putInt(NUMBER, number)
            }
        }
    }

    private val favoriteTracksViewModel: FavouriteTracksViewModel by viewModel {
        parametersOf()
    }

    private lateinit var binding: FragmentFavoriteTracksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.placeholderMessage.text = getString(R.string.empty_tracks)
        binding.placeholder.setImageResource(R.drawable.tracks_placeholder_nf)
    }
}