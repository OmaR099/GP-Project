package com.example.loginscreen.myPlants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.FragmentPlantSettingsBinding

class PlantSettings: AppCompatActivity() {
    private lateinit var binding: FragmentPlantSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlantSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}