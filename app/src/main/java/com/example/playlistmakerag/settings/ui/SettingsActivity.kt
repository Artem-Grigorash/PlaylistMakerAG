package com.example.playlistmakerag.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.playlistmakerag.R
import com.example.playlistmakerag.settings.ui.view_models.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitcher: SwitchMaterial
    private lateinit var settingsBack: ImageView
    private lateinit var settingsShare: FrameLayout
    private lateinit var settingsMail: FrameLayout
    private lateinit var settingsAgreement: FrameLayout

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setViews()

//        Creator.init(application)

        themeSwitcher.isChecked = viewModel.getChecked()
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.onThemeClicked(checked)
        }

        settingsBack.setOnClickListener {
            finish()
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
        themeSwitcher = findViewById(R.id.theme_switcher)
        settingsBack = findViewById(R.id.settings_back)
        settingsShare = findViewById(R.id.settings_share)
        settingsMail = findViewById(R.id.settings_mail)
        settingsAgreement = findViewById(R.id.settinds_agreement)
    }
}