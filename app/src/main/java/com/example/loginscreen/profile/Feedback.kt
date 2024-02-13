package com.example.loginscreen.profile

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.ActivityFeedbackBinding

class Feedback : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_btn_feedback)
        closeBtn.setOnClickListener { finish() }
    }
}