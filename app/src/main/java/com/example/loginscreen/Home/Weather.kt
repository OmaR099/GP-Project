package com.example.loginscreen.home

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Weather : AppCompatActivity() {

    val CITY: String = "Giza,6th of October"
    val API: String = "92bb9e97d8716f8568ae6cb601940256" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherTask().execute()

//        callbacks
        wind()
        pressure()
        uvIndex()
        humidity()
        close()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.weather_close_btn)
        closeBtn.setOnClickListener { finish() }
    }

    private fun wind(){
        val windBtn = findViewById<LinearLayout>(R.id.wind_ll)

        windBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://weather.com/weather-conditions/l/3573da78deabc0f1fd7085ec28dd9ddb82b7764a16ab8793935caa1abeab4270?par=samsung_widget_KSA&cm_ven=L1_condition_wind&weatherConditionsVisual=wind&theme=samsungLight")
            )
            startActivity(intent)
        }

    }

    private fun pressure(){
        val pressureBtn = findViewById<LinearLayout>(R.id.pressure_ll)

        pressureBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://weather.com/weather-conditions/l/3573da78deabc0f1fd7085ec28dd9ddb82b7764a16ab8793935caa1abeab4270?par=samsung_widget_KSA&cm_ven=L1_condition_pressure&weatherConditionsVisual=pressure&theme=samsungLight")
            )
            startActivity(intent)
        }

    }

    private fun humidity(){
        val humidityBtn = findViewById<LinearLayout>(R.id.humidity_ll)

        humidityBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://weather.com/weather-conditions/l/3573da78deabc0f1fd7085ec28dd9ddb82b7764a16ab8793935caa1abeab4270?par=samsung_widget_KSA&cm_ven=L1_condition_humidity&weatherConditionsVisual=humidity&theme=samsungLight")
            )
            startActivity(intent)
        }

    }

    private fun uvIndex(){
        val uvBtn = findViewById<LinearLayout>(R.id.uv_ll)

        uvBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://weather.com/weather-conditions/l/3573da78deabc0f1fd7085ec28dd9ddb82b7764a16ab8793935caa1abeab4270?par=samsung_widget_KSA&cm_ven=L1_condition_uv&weatherConditionsVisual=uvIndex&theme=samsungLight")
            )
            startActivity(intent)
        }

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
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
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}