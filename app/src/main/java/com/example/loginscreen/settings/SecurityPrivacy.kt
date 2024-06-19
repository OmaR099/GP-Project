package com.example.loginscreen.settings

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.ActivitySecurityPrivacyBinding

class SecurityPrivacy : AppCompatActivity() {
    private lateinit var binding: ActivitySecurityPrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()
        save()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_security_privacy)
        closeBtn.setOnClickListener { finish() }
    }

    private fun save() {
        binding.buttonSaveSettings.setOnClickListener {
            Toast.makeText(this, "Setting Saved", Toast.LENGTH_SHORT).show()
            finish() }
    }



}