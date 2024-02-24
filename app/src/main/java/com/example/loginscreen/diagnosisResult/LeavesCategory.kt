package com.example.loginscreen.diagnosisResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.LeavesCategoryBinding

class LeavesCategory: AppCompatActivity() {
    private lateinit var binding: LeavesCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LeavesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        callbacks
        cancelBtn()
    }

    private fun cancelBtn() {
        binding.cancelTv.setOnClickListener { finish() }
    }

}