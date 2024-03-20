package com.example.loginscreen.recommendations

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentLightMeterCameraBinding

class LightMeterCamera : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: FragmentLightMeterCameraBinding
    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLightMeterCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        Call backs
        openLightTipsFragment()
        setUpSensorStuff()
        closeBtn()
        doneBtn()
    }

    private fun openLightTipsFragment() {

        binding.ivCameraTips.setOnClickListener {
            val lightTipsFragment = LightTipsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, lightTipsFragment)
                .addToBackStack(null)
                .commit()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun brightness(brightness: Float): String {

        when (brightness.toInt()) {
            in 0..500 -> {binding.ivLightLevelHint.visibility = View.GONE
            binding.descTv.text = "Dark"}
            in 501..2999 -> {binding.ivLightLevelHint.visibility = View.GONE
                binding.descTv.text = "This area receives mostly bright, filtered light with little to no direct sun."}
            in 3000..7000 -> {binding.ivLightLevelHint.visibility = View.VISIBLE
                binding.descTv.text = "This area receives minimal direct sunlight, if any."}
            in 7001..20000 -> {binding.ivLightLevelHint.visibility = View.VISIBLE
                binding.descTv.text = "This area gets some direct sun (4-6 hours) but also shade during the day."}
            in 20001..70000 -> {binding.ivLightLevelHint.visibility = View.VISIBLE
                binding.descTv.text = "This area receives direct sunlight for most of the day (at least 6 hours)."}
            else -> {binding.ivLightLevelHint.visibility = View.VISIBLE
                binding.descTv.text = "This area receives direct sunlight for most of the day (at least 6 hours)."}
        }

        return when (brightness.toInt()) {
            in 0..500 -> "Dark"
            in 501..2999 -> "Indirect Light"
            in 3000..7000 -> "Full Shade"
            in 7001..20000 -> "Partial Sun"
            in 20001..70000 -> "Full Sun"
            else -> "Full Sun"
        }
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = event.values[0]

            binding.tvLightLevelDesc.visibility = View.VISIBLE
            binding.tvLightLevelDesc.text = "Sensor: $light1\n${brightness(light1)}"
            binding.lightProgressBar.setProgressWithAnimation(light1)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun closeBtn(){
        binding.ivClose.setOnClickListener { finish() }

    }

    private fun doneBtn(){
        binding.tvSubmit.setOnClickListener { finish() }

    }
}