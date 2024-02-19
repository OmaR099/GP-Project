package com.example.loginscreen.diagnosisResult

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.ActivityResultBinding
import com.example.loginscreen.home.AskExpertFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

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
        downloadImageLongPress()
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

    private fun downloadImageLongPress(){
        // download image with long press on it
        binding.imgPlantDiseaseImgSmall.setOnLongClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return@setOnLongClickListener true
        }

    }

    //download image to device
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                AlertDialog.Builder(this).setTitle("Download Image?")
                    .setMessage("Do you want to download this image to your device?")
                    .setPositiveButton("Yes") { _, _ ->
                        val drawable: BitmapDrawable = binding.imgPlantDiseaseImgSmall.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        downloadImage(bitmap)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                Toast.makeText(this, "Please allow permission to download image", Toast.LENGTH_LONG)
                    .show()
            }
        }

    //fun that take bitmap and save it in device
    private fun downloadImage(mBitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "Birds_Images" + System.currentTimeMillis() / 1000
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        }
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        if (uri != null) {
            contentResolver.insert(uri, contentValues)?.also {
                contentResolver.openOutputStream(it).use { outputStream ->
                    if (!mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream!!)) {
                        throw IOException("Couldn't Save the bitmap")
                    } else {
                        Toast.makeText(applicationContext, "Image Saved", Toast.LENGTH_SHORT).show()
                    }
                }
                return it
            }
        }
        return null
    }
}