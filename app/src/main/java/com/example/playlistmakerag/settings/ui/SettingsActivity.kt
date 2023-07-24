package com.example.playlistmakerag.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmakerag.app.DARK_THEME_KEY
import com.example.playlistmakerag.app.PREFERENCES
import com.example.playlistmakerag.R
import com.example.playlistmakerag.settings.ui.view_models.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitcher : SwitchMaterial
    private lateinit var settingsBack : ImageView
    private lateinit var settingsShare : FrameLayout
    private lateinit var settingsMail : FrameLayout
    private lateinit var settingsAgreement : FrameLayout

    private lateinit var viewModel : SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setViews()

        viewModel = ViewModelProvider(this, SettingsViewModel.getSharingViewModelFactory())[SettingsViewModel::class.java]

        val sharedPref = viewModel.provideSharedPreferences(applicationContext)
        themeSwitcher.isChecked = (sharedPref.getBoolean(DARK_THEME_KEY, false))
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.onThemeClicked(checked, applicationContext, sharedPref)
        }

        settingsBack.setOnClickListener {
            finish()
        }
        val context = applicationContext
        settingsShare.setOnClickListener {
            val shareIntent = viewModel.shareApp(context)
            startActivity(shareIntent)
        }

        settingsMail.setOnClickListener{
            val mailIntent = viewModel.openSupport(context)
            startActivity(mailIntent)
        }

        settingsAgreement.setOnClickListener{
            val settingsIntent = viewModel.openTerms(context)
            startActivity(settingsIntent)
        }
    }
    private fun setViews(){
        themeSwitcher = findViewById(R.id.theme_switcher)
        settingsBack = findViewById(R.id.settings_back)
        settingsShare = findViewById(R.id.settings_share)
        settingsMail = findViewById(R.id.settings_mail)
        settingsAgreement = findViewById(R.id.settinds_agreement)
    }
}