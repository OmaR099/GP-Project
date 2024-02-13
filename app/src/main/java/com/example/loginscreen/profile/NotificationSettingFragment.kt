package com.example.loginscreen.profile

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentNotificationSettingBinding

class NotificationSettingFragment : AppCompatActivity() {
    private lateinit var binding: FragmentNotificationSettingBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = FragmentNotificationSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()

    }

    private fun close() {
        binding.iconBack.setOnClickListener { finish() }
    }
}