package com.example.playlistmakerag.settings.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.playlistmakerag.app.App
import com.example.playlistmakerag.app.DARK_THEME_KEY
import com.example.playlistmakerag.app.PRFERENCES
import com.example.playlistmakerag.R
import com.google.android.material.switchmaterial.SwitchMaterial
class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitcher : SwitchMaterial
    private lateinit var settingsBack : ImageView
    private lateinit var settingsShare : FrameLayout
    private lateinit var settingsMail : FrameLayout
    private lateinit var settingsAgreement : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setViews()

        val sharedPref = getSharedPreferences(PRFERENCES, MODE_PRIVATE)

        themeSwitcher.isChecked = (sharedPref.getBoolean(DARK_THEME_KEY, false))

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            sharedPref.edit()
                .putBoolean(DARK_THEME_KEY, checked)
                .apply()
        }

        settingsBack.setOnClickListener {
            finish()
        }

        settingsShare.setOnClickListener {

        }

        settingsMail.setOnClickListener{

        }

        settingsAgreement.setOnClickListener{

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