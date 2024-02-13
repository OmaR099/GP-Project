package com.example.loginscreen.home

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentAskExpertBinding

class AskExpertFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAskExpertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAskExpertBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.expert_close)
        closeBtn.setOnClickListener { finish() }
    }
}