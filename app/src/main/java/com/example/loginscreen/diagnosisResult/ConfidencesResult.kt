package com.example.loginscreen.diagnosisResult

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentConfidencesBinding

class ConfidencesResult: AppCompatActivity() {
    private lateinit var binding: FragmentConfidencesBinding
    private lateinit var textView: TextView
    private lateinit var conf: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfidencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        conf = findViewById(R.id.value_tv)


        val it = intent.getStringExtra("confidences")
        val result = intent.getStringExtra("result")
        val byteArray = intent.getByteArrayExtra("image")
        val imageBitmap = byteArray?.let { it1 -> BitmapFactory.decodeByteArray(byteArray, 0, it1.size) }


        binding.valueTv.text = it.toString()
        binding.clickHere.text = result.toString()
        binding.image.setImageBitmap(imageBitmap)

        textView = findViewById(R.id.firebase)


//        database = FirebaseDatabase.getInstance()
//        ref = database.getReference("FirstName")
//
//        ref.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val name: String = snapshot.getValue(String::class.java)!!
//                textView.text = name
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                val er: String = error.message
//                Toast.makeText(applicationContext, er, Toast.LENGTH_LONG).show()
//            }
//        })
        }
    }