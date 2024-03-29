package com.example.loginscreen.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.loginscreen.R
import com.example.loginscreen.categories.Flowers
import com.example.loginscreen.categories.Fruits
import com.example.loginscreen.categories.PlantLeaf
import com.example.loginscreen.categories.Trees
import com.example.loginscreen.categories.Vegetables
import com.example.loginscreen.databinding.ActivityHomeBinding
import com.example.loginscreen.diagnosisResult.DiagnosisResult
import com.example.loginscreen.moreService.PestsIdentification
import com.example.loginscreen.myPlants.MyPlants
import com.example.loginscreen.profile.NotificationSettingFragment
import com.example.loginscreen.recommendations.LightMeterFragment
import com.example.loginscreen.recommendations.PlantTemp
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
        searchBtn()
        notification()
        askExpert()
        reminder()
        lightMeter()
        weather()
        fruits()
        vegetables()
        idealTemp()
        flowers()
        trees()
        plantLeaf()
        pestsBtn()

        binding.bottomNavigation.background = null

        makeStatusBarTransparent()

    }

    private fun makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun weather() {
        binding.weatherBtn.setOnClickListener { startActivity(Intent(this, Weather::class.java)) }
    }

    private fun lightMeter() {
        binding.lightBtn.setOnClickListener { startActivity(Intent(this, LightMeterFragment::class.java)) }
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

    private fun notification() {
        binding.imageView6.setOnClickListener { startActivity(Intent(this, NotificationSettingFragment::class.java)) }
    }

    private fun fruits() {
        binding.llFruits.setOnClickListener { startActivity(Intent(this, Fruits::class.java)) }
    }

    private fun vegetables() {
        binding.vegetablesCV.setOnClickListener { startActivity(Intent(this, Vegetables::class.java)) }
    }

    private fun flowers() {
        binding.cvFlowers.setOnClickListener { startActivity(Intent(this, Flowers::class.java)) }
    }

    private fun trees() {
        binding.cvTrees.setOnClickListener { startActivity(Intent(this, Trees::class.java)) }
    }

    private fun plantLeaf() {
        binding.cvPlantLeaf.setOnClickListener { startActivity(Intent(this, PlantLeaf::class.java)) }
    }

    private fun idealTemp() {
        binding.tempBtn.setOnClickListener { startActivity(Intent(this, PlantTemp::class.java)) }
    }

    private fun pestsBtn() {
        binding.pestBtn.setOnClickListener { startActivity(Intent(this, PestsIdentification::class.java)) }
    }

    private fun profileBtn() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.my_plant -> startActivity(Intent(this, MyPlants::class.java))
                R.id.ic_camera -> startActivity(Intent(this, DiagnosisResult::class.java))
                R.id.market -> Toast.makeText(this, "Market", Toast.LENGTH_SHORT).show()
                R.id.home_menu -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                else -> {
                }
            }
            true

        }
    }
}