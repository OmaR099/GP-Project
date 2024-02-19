package com.example.loginscreen.recommendations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentLightMeterCameraBinding

class LightMeterCamera : AppCompatActivity() {
    private lateinit var binding: FragmentLightMeterCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLightMeterCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openLightTipsFragment()

    }

    private fun openLightTipsFragment() {

        binding.ivCameraTips.setOnClickListener {
            val lightTipsFragment = LightTipsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, lightTipsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}