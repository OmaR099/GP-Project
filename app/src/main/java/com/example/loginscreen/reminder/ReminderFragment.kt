package com.example.loginscreen.reminder

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentReminderBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReminderFragment:  AppCompatActivity(){
    private lateinit var binding: FragmentReminderBinding
    val CITY: String = "Giza,6th of October"
    val API: String = "92bb9e97d8716f8568ae6cb601940256" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vsContainerEmpty.run { visibility = View.VISIBLE }

        val addReminder = findViewById<LinearLayout>(R.id.ll_add_plant_btn)

        addReminder.setOnClickListener { startActivity(Intent(this, ReminderSetting::class.java)) }

        binding.locationToAllowBtn.setOnClickListener { weatherTask().execute() }

//        CallBacks

    }


    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            binding.locationToAllowBtn.visibility = View.GONE


        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")

                val updatedAt:Long = jsonObj.getLong("dt")
                val temp = main.getString("temp")+"°C"
                
                findViewById<TextView>(R.id.location_to_allow_notice).text = temp

            } catch (e: Exception) {
            }

        }
    }
}