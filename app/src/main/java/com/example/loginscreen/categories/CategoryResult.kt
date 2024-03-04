package com.example.loginscreen.categories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentCategoryResultBinding
import com.example.loginscreen.home.AskExpertFragment

class CategoryResult: AppCompatActivity() {

    private lateinit var binding: FragmentCategoryResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoryResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val overview = intent.getStringExtra("overview")
        val pruning = intent.getStringExtra("pruning")
        val watering = intent.getStringExtra("watering")
        val temperature = intent.getStringExtra("temperature")
        val fertilizing = intent.getStringExtra("fertilizing")
        val site = intent.getStringExtra("site")
        val imageId = intent.getIntExtra("imageId", R.drawable.apple_fruit)

        binding.nameTitle.text = name
        binding.descTitle.text = desc
        binding.plantOverview.text = overview
        binding.plantPruning.text = pruning
        binding.watering.text = watering
        binding.plantTemp.text = temperature
        binding.plantFert.text = fertilizing
        binding.plantSite.text = site
        binding.imgPlant.setImageResource(imageId)

        binding.imgClose.setOnClickListener { finish() }

//        Callbacks
        plantSearch()
        overviewSearch()
        wateringSearch()
        fertilizingSearch()
        temperatureSearch()
        siteSearch()
        pruningSearch()
        askExpert()

    }

    private fun plantSearch(){
        binding.nameTitle.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun overviewSearch(){
        binding.llOverview.setOnClickListener {
            val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/search?q=Overview about ${binding.nameTitle.text} ${binding.descTitle.text}")
        )
            startActivity(intent) }

    }

    private fun wateringSearch(){
        binding.llWatering.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=Watering of ${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun fertilizingSearch(){
        binding.llFert.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=Fertilizing of ${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun temperatureSearch(){
        binding.llTemp.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=Temperature of ${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun siteSearch(){
        binding.llSite.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=Sunlight for ${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun pruningSearch(){
        binding.llPruning.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=Pruning of ${binding.nameTitle.text} ${binding.descTitle.text}")
            )
            startActivity(intent) }

    }

    private fun askExpert(){binding.askExpertsTv.setOnClickListener {
        startActivity(Intent(this, AskExpertFragment::class.java)) }}
}