package com.example.playlistmakerag.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.playlistmakerag.R
import com.example.playlistmakerag.databinding.FragmentSettingsBinding
import com.example.playlistmakerag.settings.ui.view_models.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: Fragment() {

    private lateinit var themeSwitcher: SwitchMaterial
    private lateinit var settingsShare: FrameLayout
    private lateinit var settingsMail: FrameLayout
    private lateinit var settingsAgreement: FrameLayout

    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View    {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()

        themeSwitcher.isChecked = viewModel.getChecked()
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.onThemeClicked(checked)
        }

        settingsShare.setOnClickListener {
            val shareIntent = viewModel.shareApp(getString(R.string.practicum_url))
            startActivity(shareIntent)
        }

        settingsMail.setOnClickListener {
            val mailIntent =
                viewModel.openSupport(getString(R.string.thanks), getString(R.string.subject))
            startActivity(mailIntent)
        }

        settingsAgreement.setOnClickListener {
            val settingsIntent = viewModel.openTerms(getString(R.string.legal_url))
            startActivity(settingsIntent)
        }
    }

    private fun setViews() {
        themeSwitcher = binding.themeSwitcher
        settingsShare = binding.settingsShare
        settingsMail = binding.settingsMail
        settingsAgreement = binding.settindsAgreement
    }


}