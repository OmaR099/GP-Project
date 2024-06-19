package com.example.loginscreen.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.ActivitySettingBinding
import com.example.loginscreen.settings.AboutUs
import com.example.loginscreen.settings.Faq
import com.example.loginscreen.settings.SecurityPrivacy

class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()
        aboutUs()
        security()
        faq()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_setting)
        closeBtn.setOnClickListener { finish() }
    }

    private fun aboutUs() {
        binding.aboutUsRL.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }
    }

    private fun security() {
        binding.securityRL.setOnClickListener {
            startActivity(Intent(this, SecurityPrivacy::class.java))
        }
    }

    private fun faq() {
        binding.faqRL.setOnClickListener {
            startActivity(Intent(this, Faq::class.java))
        }
    }
}