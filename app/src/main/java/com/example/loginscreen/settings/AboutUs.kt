package com.example.loginscreen.settings

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.AboutUsBinding

class AboutUs : AppCompatActivity() {
    private lateinit var binding: AboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()
        done()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_about)
        closeBtn.setOnClickListener { finish() }
    }
    private fun done() {
        binding.button.setOnClickListener { finish() }
    }

}