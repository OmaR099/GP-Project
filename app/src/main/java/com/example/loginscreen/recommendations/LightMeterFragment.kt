package com.example.loginscreen.recommendations

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentLightMeterBinding

class LightMeterFragment : AppCompatActivity() {
    private lateinit var binding: FragmentLightMeterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLightMeterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CallBacks
        close()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.close_btn)
        closeBtn.setOnClickListener { finish() }
    }
}