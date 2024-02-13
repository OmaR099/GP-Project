package com.example.loginscreen.diagnosisResult

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentConfidencesBinding

class ConfidencesResult: AppCompatActivity() {
    private lateinit var binding: FragmentConfidencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfidencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val it = intent.getStringExtra("confidences")
        binding.valueTv.text = it.toString()
    }
}