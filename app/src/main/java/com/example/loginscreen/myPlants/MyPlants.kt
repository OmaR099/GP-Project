package com.example.loginscreen.myPlants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentMyPlantsBinding

class MyPlants: AppCompatActivity() {
    private lateinit var binding: FragmentMyPlantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMyPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}