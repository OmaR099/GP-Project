package com.example.loginscreen.search

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.ActivitySearchPlantBinding

class SearchPlantActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySearchPlantBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivitySearchPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cancelBtn()
    }

    private fun cancelBtn(){
        binding.cancelTv.setOnClickListener { finish() }
    }


}