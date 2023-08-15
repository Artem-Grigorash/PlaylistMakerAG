package com.example.playlistmakerag.mediateka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentFavoriteTracksBinding


class FragmentFavoriteTracks : Fragment() {

    companion object {
        private const val NUMBER = "number"

        fun newInstance(number: Int) = FragmentFavoriteTracks().apply {
            arguments = Bundle().apply {
                putInt(NUMBER, number)
            }
        }
    }

    private lateinit var binding: FragmentFavoriteTracksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(requireArguments().getInt(NUMBER)) {
            1 -> {
                binding.placeholderMessage.text = "Ваша медиатека пуста"
                binding.placeholder.setImageResource(R.drawable.tracks_placeholder_nf)
                binding.cleanHistoryButton.visibility = View.GONE
            }
            2 -> {
                binding.placeholderMessage.text = "Вы не создали\nни одного плейлиста"
                binding.placeholderMessage.isAllCaps = false
                binding.placeholder.setImageResource(R.drawable.tracks_placeholder_nf)
                binding.cleanHistoryButton.visibility = View.VISIBLE
            }
        }
    }

}