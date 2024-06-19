package com.example.loginscreen.categories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.chatBot.ChatBot
import com.example.loginscreen.databinding.FragmentCategoryDiseasesResultBinding
import com.example.loginscreen.databinding.FragmentCategoryResultBinding
import com.example.loginscreen.home.AskExpertFragment

class CategoryDiseaseResult: AppCompatActivity() {

    private lateinit var binding: FragmentCategoryDiseasesResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoryDiseasesResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val overview = intent.getStringExtra("overview")
        val watering = intent.getStringExtra("watering")
        val temperature = intent.getStringExtra("temperature")
        val fertilizing = intent.getStringExtra("fertilizing")
        val imageId = intent.getIntExtra("imageId", R.drawable.apple_fruit)


        binding.titleOfDisease.text = name
        binding.plantOverview.text = overview
        binding.watering.text = watering
        binding.plantTemp.text = temperature
        binding.plantFert.text = fertilizing
        binding.imgPlant.setImageResource(imageId)

        binding.imgClose.setOnClickListener { finish() }

//        Callbacks
//        overviewSearch()
//        wateringSearch()
//        fertilizingSearch()
//        temperatureSearch()
        askExpert()
        askChatBott()

    }


//    private fun overviewSearch(){
//        binding.llOverview.setOnClickListener {
//            val intent = Intent(
//            Intent.ACTION_VIEW,
//            Uri.parse("https://www.google.com/search?q=Overview about ${binding.titleOfDisease.text}")
//        )
//            startActivity(intent) }
//
//    }
//
//    private fun wateringSearch(){
//        binding.llWatering.setOnClickListener {
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://www.google.com/search?q=Symptom of ${binding.titleOfDisease.text}")
//            )
//            startActivity(intent) }
//
//    }
//
//    private fun fertilizingSearch(){
//        binding.llFert.setOnClickListener {
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://www.google.com/search?q=Solutions for ${binding.titleOfDisease.text}")
//            )
//            startActivity(intent) }
//
//    }
//
//    private fun temperatureSearch(){
//        binding.llTemp.setOnClickListener {
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://www.google.com/search?q=Prevention for ${binding.titleOfDisease.text}")
//            )
//            startActivity(intent) }
//
//    }



    private fun askExpert(){binding.askExpertsTv.setOnClickListener {
        startActivity(Intent(this, AskExpertFragment::class.java)) }}
    private fun askChatBott(){binding.askChatBotTv.setOnClickListener {
        startActivity(Intent(this, ChatBot::class.java)) }}
}