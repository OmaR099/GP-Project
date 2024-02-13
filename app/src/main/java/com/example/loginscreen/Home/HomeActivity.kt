package com.example.loginscreen.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.loginscreen.R
import com.example.loginscreen.databinding.ActivityHomeBinding
import com.example.loginscreen.diagnosisResult.DiagnosisResult
import com.example.loginscreen.home.AskExpertFragment
import com.example.loginscreen.recommendations.LightMeterFragment
import com.example.loginscreen.reminder.ReminderFragment
import com.example.loginscreen.search.SearchPlantActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    // CallBacks
        profileBtn()
        cameraBtn()
        searchBtn()
        askExpert()
        reminder()
        weather()

    }

    private fun weather() {
        binding.weatherBtn.setOnClickListener { startActivity(Intent(this, LightMeterFragment::class.java)) }
    }

    private fun reminder() {
        binding.reminderBtn.setOnClickListener { startActivity(Intent(this, ReminderFragment::class.java)) }
    }

    private fun askExpert() {
        binding.askExpertBtn.setOnClickListener { startActivity(Intent(this, AskExpertFragment::class.java)) }
    }

    private fun searchBtn() {
        binding.searchBtn.setOnClickListener { startActivity(Intent(this, SearchPlantActivity::class.java)) }
    }

    private fun cameraBtn() {
        binding.cameraBt.setOnClickListener { startActivity(Intent(this, DiagnosisResult::class.java)) }
    }

    private fun profileBtn() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.my_plant -> Toast.makeText(this, "My plants", Toast.LENGTH_SHORT).show()
                R.id.market -> Toast.makeText(this, "Market", Toast.LENGTH_SHORT).show()
                R.id.home_menu -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                else -> {
                }
            }
            true

        }
    }
}