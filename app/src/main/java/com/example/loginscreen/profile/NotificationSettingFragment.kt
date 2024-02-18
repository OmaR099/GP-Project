package com.example.loginscreen.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentNotificationSettingBinding

class NotificationSettingFragment : AppCompatActivity() {
    private lateinit var binding: FragmentNotificationSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNotificationSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        close()
    }

    private fun close() {
        binding.iconBack.setOnClickListener { finish() }
    }
}