package com.example.loginscreen.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginscreen.R
import com.example.loginscreen.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileBtn()

    }

    private fun profileBtn (){

        binding.icProfile.setOnClickListener {

            startActivity(Intent(this, ProfileActivity::class.java))

        }

    }
}