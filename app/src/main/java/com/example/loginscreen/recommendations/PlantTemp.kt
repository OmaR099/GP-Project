package com.example.loginscreen.recommendations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.categories.Flowers
import com.example.loginscreen.categories.Fruits
import com.example.loginscreen.categories.Trees
import com.example.loginscreen.categories.Vegetables
import com.example.loginscreen.databinding.FragmentPlantTempBinding
import org.json.JSONObject
import java.net.URL

class PlantTemp: AppCompatActivity() {
    private lateinit var binding: FragmentPlantTempBinding
    var CITY: String = "Giza"
    val API: String = "92bb9e97d8716f8568ae6cb601940256" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlantTempBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.parisBtn.isEnabled = false

        binding.autoCompleteCites.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.parisBtn.animate().alpha(0.5f).duration = 300
                    binding.parisBtn.isEnabled = false
                } else {
                    binding.parisBtn.animate().alpha(1f).duration = 300
                    binding.parisBtn.isEnabled = true
                }
            }
        })

//        binding.locationToAllowBtn.setOnClickListener {
//            CITY = "Giza"
//            weatherTask().execute() }
        binding.parisBtn.setOnClickListener {
            CITY = binding.autoCompleteCites.text.toString()
            weatherTask().execute() }

        cities()
        fruits()
        vegetables()
        trees()
        flowers()
        close()

    }

    private fun close() {
        val closeBtn: ImageView = findViewById(R.id.ideal_close_btn)
        closeBtn.setOnClickListener { finish() }
    }

    private fun cities() {
        val frequentlyWater = resources.getStringArray(R.array.cites)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, frequentlyWater)
        binding.autoCompleteCites.setAdapter(arrayAdapter)
    }

    private fun fruits(){
        binding.fruitsTitle.setOnClickListener { startActivity(Intent(this, Fruits::class.java)) }
    }

    private fun vegetables(){
        binding.vegetablesTitle.setOnClickListener { startActivity(Intent(this, Vegetables::class.java)) }
    }

    private fun trees(){
        binding.treesTitle.setOnClickListener { startActivity(Intent(this, Trees::class.java)) }
    }

    private fun flowers(){
        binding.flowersTitle.setOnClickListener { startActivity(Intent(this, Flowers::class.java)) }
    }
    @SuppressLint("StaticFieldLeak")
    inner class weatherTask() : AsyncTask<String, Void, String>() {

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()


        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String? {
            val response:String? = try{
                URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                null
            }
            return response
        }

        @SuppressLint("SetTextI18n")
        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result.toString())
                val main = jsonObj.getJSONObject("main")

                val updatedAt:Long = jsonObj.getLong("dt")
                val tempDouble = main.getDouble("temp")
                val tempInt = tempDouble.toInt()
                val temp = "$tempInt°C"

                findViewById<TextView>(R.id.location_to_allow_notice).text = temp

                when (tempInt){
                    in 0..9 -> {binding.testTv.text = "-Apple (Night)\n- Cherry (Winter chill)"
                        binding.vegetablesTv.text = "N/A"
                        binding.treeTv.text = "- Black walnut (Winter)\n- Japanese maple (Winter)"
                        binding.flowerTv.text = "N/A"}

                    in 10..12 ->{binding.testTv.text = "N/A"
                        binding.vegetablesTv.text = "- Cabbage\n- Garlic\n- Turnip"
                        binding.treeTv.text = "N/A"
                        binding.flowerTv.text = "N/A"}

                    in 13..14 ->{binding.testTv.text = "- Cloudberry"
                        binding.vegetablesTv.text = "- Cabbage\n- Carrot\n- Garlic\n- Lettuce\n- Mushrooms\n- Onion\n- Potato (Tubers)\n- Spinach\n- Turnip"
                        binding.treeTv.text = "- Flowering dogwood"
                        binding.flowerTv.text = "- Foxgloves\n- Hostas\n- Sweet William"}

                    in 15..17 -> {binding.testTv.text = "- Cloudberry\n- Strawberry"
                        binding.vegetablesTv.text = "- Broccoli\n- Cabbage\n- Carrot\n- Garlic\n- Lettuce\n- Mushrooms\n- Onion\n- Potato (Tubers)\n- Spinach\n- Tomato (Night)"
                        binding.treeTv.text = "- Flowering dogwood"
                        binding.flowerTv.text = "- Foxgloves\n- Hostas\n- Sweet William"}

                    18 -> {binding.testTv.text = "- Apple\n- Blackberry\n- Blueberry\n- Cherry\n- Cloudberry\n- Crab Apple\n- Grape\n- Honeyberry\n- Kiwi\n- Lemon\n- Loganberry\n- Orange\n- Peach\n- Raspberry\n- Strawberry"
                        binding.vegetablesTv.text = "- Broccoli\n- Cabbage\n- Carrot\n- Garlic\n- Lettuce\n- Mushrooms\n- Onion\n- Potato (Tubers)\n- Spinach\n- Tomato (Night)"
                        binding.treeTv.text = "- Black walnut\n- Cherry plum\n- Flowering dogwood\n- Japanese maple\n- Japanese privet\n- Rubber plant\n- Weeping fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Daylilies\n- Foxgloves\n- Hostas\n- Impatiens\n- Lavender\n- Marigolds\n- Petunias\n- Sweet William\n- Zinnias"}

                    19 -> {binding.testTv.text = "- Apple\n- Blackberry\n- Blueberry\n- Cherry\n- Crab Apple\n- Grape\n- Honeyberry\n- Kiwi\n- Lemon\n- Loganberry\n- Orange\n- Peach\n- Raspberry\n- Strawberry"
                        binding.vegetablesTv.text = "- Broccoli\n- Potato (Tops)"
                        binding.treeTv.text = "- Black walnut\n- Cherry plum\n- Flowering dogwood\n- Japanese maple\n- Japanese privet\n- Rubber plant\n- Weeping fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Daylilies\n- Impatiens\n- Lavender\n- Marigolds\n- Petunias\n- Zinnias"}

                    in 20..21 ->{binding.testTv.text = "- Apple\n- Avocado\n- Blackberry\n- Blueberry\n- Cherry\n- Crab Apple\n- Grape\n- Honeyberry\n- Kiwi\n- Lemon\n- Loganberry\n- Lychee\n- Orange\n- Peach\n- Raspberry\n- Strawberry"
                        binding.vegetablesTv.text = "- Bell pepper\n- Broccoli\n- Chili pepper\n- Potato (Tops)\n- Tomato (Day)"
                        binding.treeTv.text = "- Black walnut\n- Cherry plum\n- Curtain fig\n- Flowering dogwood\n- Japanese maple\n- Japanese privet\n- Rubber plant\n- Weeping fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Daylilies\n- Foxgloves\n- Hostas\n- Impatiens\n- Lavender\n- Marigolds\n- Petunias\n- Sweet William\n- Zinnias"}

                    in 22..23 ->{binding.testTv.text = "- Apple\n- Avocado\n- Blackberry\n- Blueberry\n- Cherry\n- Crab Apple\n- Grape\n- Honeyberry\n- Kiwi\n- Lemon\n- Loganberry\n- Lychee\n- Orange\n- Peach\n- Raspberry"
                        binding.vegetablesTv.text = "- Bell pepper\n- Chili pepper\n- Potato (Tops)\n- Tomato (Day)"
                        binding.treeTv.text = "- Black walnut\n- Cherry plum\n- Curtain fig\n- Flowering dogwood\n- Japanese maple\n- Japanese privet\n- Rubber plant\n- Weeping fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Daylilies\n- Foxgloves\n- Hostas\n- Impatiens\n- Lavender\n- Marigolds\n- Petunias\n- Sweet William\n- Zinnias"}

                    24 -> {binding.testTv.text = "- Apple\n- Avocado\n- Banana\n- Blackberry\n- Blueberry\n- Cherry\n- Coconut\n- Crab Apple\n- Grape\n- Honeyberry\n- Kiwi\n- Lemon\n- Loganberry\n- Lychee\n- Mango\n- Orange\n- Peach\n- Raspberry\n- Watermelon"
                        binding.vegetablesTv.text = "- Bell pepper\n- Chili pepper\n- Potato (Tops)\n- Tomato (Day)"
                        binding.treeTv.text = "- Black walnut\n- Cherry plum\n- Curtain fig\n- Flowering dogwood\n- Japanese maple\n- Japanese privet\n- Rubber plant\n- Weeping fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Daylilies\n- Foxgloves\n- Hostas\n- Impatiens\n- Lavender\n- Marigolds\n- Petunias\n- Sweet William\n- Zinnias"}

                    in 25..28 -> {binding.testTv.text = "- Avocado\n- Banana\n- Coconut\n- Lychee\n- Mango\n- Watermelon"
                        binding.vegetablesTv.text = "- Bell pepper\n- Chili pepper\n- Tomato (Day)"
                        binding.treeTv.text = "- Curtain fig"
                        binding.flowerTv.text = "- Black-eyed Susans\n- China Rose\n- Coneflowers\n- Foxgloves\n- Hostas\n- Lavender\n- Marigolds\n- Sweet William\n- Zinnias"}

                    in 29..30 -> {binding.testTv.text = "- Avocado"
                        binding.vegetablesTv.text = "N/A"
                        binding.treeTv.text = "N/A"
                        binding.flowerTv.text = "N/A"}

                    in 31..50 -> {binding.testTv.text = "N/A"
                        binding.vegetablesTv.text = "N/A"
                        binding.treeTv.text = "N/A"
                        binding.flowerTv.text = "N/A"}
                }
            } catch (_: Exception) {
            }
        }
    }
}
