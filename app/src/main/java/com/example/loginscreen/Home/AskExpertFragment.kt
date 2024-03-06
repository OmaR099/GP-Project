package com.example.loginscreen.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentAskExpertBinding

class AskExpertFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAskExpertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAskExpertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSend.isEnabled = false


//        CallBacks
        close()
        sendMailBtn()
        freqWater()
        careMenu()
        generalLocationMenu()
        furtherDetails()
        plantType()
        emailMenu()
        plantCond()

    }

    private fun freqWater() {
        val frequentlyWater = resources.getStringArray(R.array.frequently_water)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompleteFrequentlyWater.setAdapter(arrayAdapter)
    }


    private fun careMenu() {
        val frequentlyWater = resources.getStringArray(R.array.cared)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompleteCare.setAdapter(arrayAdapter)
    }

    private fun emailMenu() {
        val email = resources.getStringArray(R.array.email)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, email)
        binding.autoCompleteEmail.setAdapter(arrayAdapter)
    }

    private fun generalLocationMenu() {
        val frequentlyWater = resources.getStringArray(R.array.general_Location)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompleteGeneralLocation.setAdapter(arrayAdapter)
    }

    private fun furtherDetails() {
        val frequentlyWater = resources.getStringArray(R.array.further_details)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompleteFurtherDetails.setAdapter(arrayAdapter)
    }

    private fun plantCond() {
        val frequentlyWater = resources.getStringArray(R.array.plantCond)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompletePlantCond.setAdapter(arrayAdapter)

        binding.autoCompletePlantCond.setOnItemClickListener { adapterView, view, position, id ->
            if (adapterView.getItemAtPosition(position).toString() == "Healthy") {
                binding.autoCompleteSubject.setText("")
                val email = resources.getStringArray(R.array.subjectHealthy)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteSubject.setAdapter(arrayAdapterEmail)
            }else if (adapterView.getItemAtPosition(position).toString() == "Showing signs of distress"){
                binding.autoCompleteSubject.setText("")
                val email = resources.getStringArray(R.array.subjectDisease)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteSubject.setAdapter(arrayAdapterEmail)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun plantType() {
        val frequentlyWater = resources.getStringArray(R.array.plant_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompletePlantType.setAdapter(arrayAdapter)

        binding.autoCompletePlantType.setOnItemClickListener { adapterView, view, position, id ->
            if (adapterView.getItemAtPosition(position).toString() == "Fruit tree") {
                binding.autoCompleteEmail.setText("omer.osama07@gmail.com")
                val email = resources.getStringArray(R.array.emailFruits)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteEmail.setAdapter(arrayAdapterEmail)

                binding.autoCompleteSpecificType.setText("")
                val category = resources.getStringArray(R.array.fruitsTrees)
                val arrayAdapterCategory = ArrayAdapter(this, R.layout.dropdown_item, category)
                binding.autoCompleteSpecificType.setAdapter(arrayAdapterCategory)

            }else if (adapterView.getItemAtPosition(position).toString() == "Houseplant"){
                binding.autoCompleteEmail.setText("HouseplantHelper@gmail.com")
                val email = resources.getStringArray(R.array.emailHouseplant)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteEmail.setAdapter(arrayAdapterEmail)

                binding.autoCompleteSpecificType.setText("")
                val category = resources.getStringArray(R.array.housePlant)
                val arrayAdapterCategory = ArrayAdapter(this, R.layout.dropdown_item, category)
                binding.autoCompleteSpecificType.setAdapter(arrayAdapterCategory)

            }else if (adapterView.getItemAtPosition(position).toString() == "Vegetable"){
                binding.autoCompleteEmail.setText("VegetableHelper@gmail.com")
                val email = resources.getStringArray(R.array.emailVegetable)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteEmail.setAdapter(arrayAdapterEmail)

                binding.autoCompleteSpecificType.setText("")
                val category = resources.getStringArray(R.array.vegetables)
                val arrayAdapterCategory = ArrayAdapter(this, R.layout.dropdown_item, category)
                binding.autoCompleteSpecificType.setAdapter(arrayAdapterCategory)

            }else if (adapterView.getItemAtPosition(position).toString() == "Outdoor flowering plant"){
                binding.autoCompleteEmail.setText("Dr.Gardener@gmail.com")
                val email = resources.getStringArray(R.array.emailOutdoorFloweringPlant)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteEmail.setAdapter(arrayAdapterEmail)

                binding.autoCompleteSpecificType.setText("")
                val category = resources.getStringArray(R.array.outDoorFloweringPlants)
                val arrayAdapterCategory = ArrayAdapter(this, R.layout.dropdown_item, category)
                binding.autoCompleteSpecificType.setAdapter(arrayAdapterCategory)

            } else{
                binding.autoCompleteEmail.setText("")
                val email = resources.getStringArray(R.array.email)
                val arrayAdapterEmail = ArrayAdapter(this, R.layout.dropdown_item, email)
                binding.autoCompleteEmail.setAdapter(arrayAdapterEmail)

                val category = resources.getStringArray(R.array.plantCategory)
                val arrayAdapterCategory = ArrayAdapter(this, R.layout.dropdown_item, category)
                binding.autoCompleteSpecificType.setAdapter(arrayAdapterCategory)
            }
        }
    }

    private fun sendMailBtn() {
        binding.autoCompleteEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvSend.animate().alpha(0.5f).duration = 300
                    binding.tvSend.isEnabled = false
                } else {
                    binding.tvSend.animate().alpha(1f).duration = 300
                    binding.tvSend.isEnabled = true
                }
            }
        })

        binding.tvSend.setOnClickListener {

            val email = binding.autoCompleteEmail.text.toString()
            val subject = binding.autoCompleteSubject.text.toString()
            val frequentlyWater = binding.autoCompleteFrequentlyWater.text.toString()
            val moreInfo = binding.etAddInfo.text.toString()
            val careTime = binding.autoCompleteCare.text.toString()
            val generalLocation = binding.autoCompleteGeneralLocation.text.toString()
            val furtherDetails = binding.autoCompleteFurtherDetails.text.toString()
            val stateCity = binding.etCity.text.toString()
            val plantType = binding.autoCompletePlantType.text.toString()
            val specificType = binding.autoCompleteSpecificType.text.toString()
            val message =
                "How often have you watered your plants lately?\n$frequentlyWater\n\nhow long have you cared for this plant?\n$careTime\n\nGeneral location of the plant:\n$generalLocation\n\nFurther details for the location:\n$furtherDetails\n\nState, City:\n$stateCity\n\nPlant type:\n$plantType\n\nSpecific Type\n$specificType\n\nAdditional info:\n$moreInfo"

            val addresses = email.split(",".toRegex()).toTypedArray()

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)

            }
            startActivity(intent)
        }
    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.expert_close)
        closeBtn.setOnClickListener { finish() }
    }
}