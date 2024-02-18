package com.example.loginscreen.diagnosisResult

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.ActivityResultBinding
import com.example.loginscreen.home.AskExpertFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Result: AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val it = intent.getStringExtra("confidences")
        val result = intent.getStringExtra("result")
        val byteArray = intent.getByteArrayExtra("image")
        val imageBitmap = byteArray?.let { it1 -> BitmapFactory.decodeByteArray(byteArray, 0, it1.size) }

//
        binding.textConfidences.text = it.toString()
        binding.diseaseTitle.text = result.toString()
        binding.imgPlantDiseaseImgSmall.setImageBitmap(imageBitmap)

//      CallBacks
        readData(result.toString())
        googleSearch()
        askExpert()
    }

    private fun readData(plantName: String) {

        reference = FirebaseDatabase.getInstance().getReference("Plants")
        reference.child(plantName).get().addOnSuccessListener {

            if (it.exists()) {
                val overView = it.child("overview").value
                val symptoms = it.child("symptoms").value
                val prevention = it.child("prevention").value
                val solutions = it.child("solutions").value
                val cause = it.child("cause").value
                Toast.makeText(this, "Data Read Successfully", Toast.LENGTH_SHORT).show()
                binding.diseaseOverview.text = overView.toString()
                binding.diseaseSymptom.text = symptoms.toString()
                binding.diseaseCause.text = cause.toString()
                binding.textSolutions.text = solutions.toString()
                binding.textPrevention.text = prevention.toString()

            } else {
                Toast.makeText(this, "Plant Doesn't Exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show() }
    }

    private fun googleSearch(){
        //    Google Search
        binding.diseaseTitle.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=${binding.diseaseTitle.text}")
            )
            startActivity(intent)
        }

    }

    private fun askExpert(){binding.askExpertsTv.setOnClickListener {
        startActivity(Intent(this, AskExpertFragment::class.java)) }}
}