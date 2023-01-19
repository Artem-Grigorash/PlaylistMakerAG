package com.example.playlistmakerag

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsBack = findViewById<ImageView>(R.id.settings_back)
        settingsBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val settingsShare = findViewById<FrameLayout>(R.id.settings_share)
        settingsShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val url = getString(R.string.practicum_url)
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }

        val settingsMail = findViewById<FrameLayout>(R.id.settings_mail)
        settingsMail.setOnClickListener{
            val message = getString(R.string.thanks)
            val massageTheme = getString(R.string.subject)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, massageTheme)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }

        val settingsAgreement = findViewById<FrameLayout>(R.id.settinds_agreement)
        settingsAgreement.setOnClickListener{
            val url = Uri.parse(getString(R.string.legal_url))
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
    }
}