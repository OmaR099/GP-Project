package com.example.loginscreen.reminder

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentReminderBinding

class ReminderFragment:  AppCompatActivity(){
    private lateinit var binding: FragmentReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks

    }
}