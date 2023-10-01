package com.example.playlistmakerag.mediateka.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmakerag.mediateka.ui.FavoriteTracksFragment
import com.example.playlistmakerag.mediateka.ui.PlaylistsFragment

class TracksViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            FavoriteTracksFragment.newInstance(position + 1)
        else
            PlaylistsFragment.newInstance(position+1)
    }
}