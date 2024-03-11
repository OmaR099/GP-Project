package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2

class Vegetables: AppCompatActivity() {
    private lateinit var binding: FruitsCategoryBinding
    private lateinit var userArrayList: ArrayList<User2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FruitsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        callbacks
//        search()
        cancelBtn()

        val imageId = intArrayOf(

            R.drawable.broccoli,
            R.drawable.cabbage,
            R.drawable.lettuce,
            R.drawable.spinach,
            R.drawable.mushrooms,
            R.drawable.garlic,
            R.drawable.onion,
            R.drawable.bell_pepper,
            R.drawable.chili_pepper,
            R.drawable.carrot,
            R.drawable.turnip,
            R.drawable.potato,
            R.drawable.tomato
        )

        val name = arrayOf(
            "Broccoli",
            "Cabbage",
            "Lettuce",
            "Spinach",
            "Mushrooms",
            "Garlic",
            "Onion",
            "Bell pepper",
            "Chili pepper",
            "Carrot",
            "Turnip",
            "Potato",
            "Tomato"
        )

        val desc = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "Capsicum annuum",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "Solanum tuberosum",
            "Solanum lycopersicum"
        )

        val overview = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "Bell peppers are a popular vegetable known for their vibrant colors (green, red, yellow, orange) and crisp, slightly sweet flavor. They are a great source of vitamin C and add a delightful crunch to various dishes.\n\n• Varieties: Many bell pepper varieties exist, differing in size, color, sweetness, and maturity time.\n\n• Uses: Eaten fresh, roasted, grilled, stuffed, used in stir-fries, salads, salsas, and various other dishes.\n\n• Health benefits: Excellent source of vitamin C, a good source of vitamin A, and contain antioxidants.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "Potatoes are a versatile and delicious root vegetable enjoyed worldwide. Packed with nutrients, they are a staple food in many cultures and can be prepared in countless ways.\n\n• Types: Many potato varieties exist, varying in size, skin color (brown, red, yellow, white), flesh color (white, yellow, purple), and maturity time.\n\n• Uses: Eaten mashed, roasted, fried, baked, boiled, used in soups, stews, and various other dishes.\n\n• Health benefits: Good source of vitamin C, potassium, and fiber.",
            "Tomatoes are a juicy and versatile fruit (often treated like a vegetable) enjoyed worldwide. They come in a vast array of shapes, sizes, and colors, offering a delightful range of flavors for salads, sauces, sandwiches, and countless other dishes.\n\n• Varieties: Hundreds of tomato varieties exist, with fruits ranging in size (cherry, plum, beefsteak), color (red, yellow, orange, green, black), and growth habit (determinate, indeterminate).\n\n• Uses: Eaten fresh, cooked in various dishes, used for juice, ketchup, salsa, and various other products.\n\n• Health benefits: Good source of vitamin C, potassium, and lycopene, an antioxidant."

        )

        val pruning = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "• Optional: Pruning bell pepper plants is not strictly necessary but can be beneficial for improving air circulation, light penetration, and fruit production.\n\n• Method: If you choose to prune, pinch off suckers (small shoots that grow between the main stem and branches) and remove any dead, diseased, or unproductive leaves or branches.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "• Optional: Pruning potato plants is not necessary but can be beneficial for managing growth and promoting tuber development.\n\n• Method: You can remove flower buds as they appear. This redirects the plant's energy towards producing larger tubers instead of flowers and seeds.",
            "• Method: Pruning tomato plants, particularly indeterminate varieties, encourages bushier growth, better air circulation, and increased fruit production.\n\n• Techniques: Prune off suckers (shoots that grow between the main stem and branches), and remove lower leaves as the plant matures to improve air circulation and reduce disease risk. You can also top indeterminate vines to control their height."

        )

        val watering = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "• Frequency: Water your bell pepper plants regularly, especially during hot and dry weather. Aim for 1-2 inches of water per week, delivered slowly to soak the soil deeply.\n\n• Avoid extremes: Don't let the soil dry out completely, but also avoid waterlogging.\n\n• Fruit development: Consistent moisture is crucial during fruit development to ensure firm and healthy peppers.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "• Frequency: Water your potato plants regularly, especially during hot and dry weather. Aim to keep the soil consistently moist but not soggy.\n\n• Early growth: Water moderately during the early stages of growth.\n\n• Tuber development: Increase watering frequency as the potato plants mature and tubers begin to develop underground.",
            "• Frequency: Water your tomato plants regularly, especially during hot and dry weather. Aim for 1-2 inches of water per week, delivered slowly to soak the soil deeply.\n\n• Focus on the roots: Water the base of the plant rather than the leaves to avoid promoting fungal diseases.\n\n• Fruit development: Consistent moisture is crucial during fruit development to prevent blossom end rot and ensure juicy tomatoes."

        )

        val fertilizing = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "• Time: Fertilize your pepper plants once a month during the growing season (from spring to early fall in most regions) with a balanced fertilizer formulated for vegetables.\n\n• Amount: Follow the instructions on the fertilizer package for the recommended application rate.\n\n• Avoid excess nitrogen: Avoid fertilizers high in nitrogen, as this can promote leafy growth at the expense of fruit production.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "• Time: Fertilize your potato plants once a month during the growing season (from spring to mid-summer in most regions) with a fertilizer formulated for vegetables.\n\n• Amount: Follow the instructions on the fertilizer package for the recommended application rate.\n\n• Focus on potassium: Choose a fertilizer with a higher potassium content compared to nitrogen, as potassium promotes healthy tuber development.",
            "• Time: Fertilize your tomato plants every 2-4 weeks during the growing season (from spring to fall in most regions) with a balanced fertilizer formulated for vegetables or tomatoes.\n\n• Amount: Follow the instructions on the fertilizer package for the recommended application rate.\n\n• Organic option: If using organic methods, compost tea or aged manure can be used as a nutrient source."

        )

        val temperature = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "• Ideal: Bell peppers are warm-season vegetables and thrive in hot and sunny weather.\n\n• Specifics: The ideal temperature range for growing bell peppers is between 21°C to 29°C (70°F to 85°F). They are sensitive to frost and cold temperatures.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "• Ideal: Potatoes prefer cool weather conditions.\n\n• Specifics: The ideal temperature range for growing potatoes is between 13°C to 21°C (55°F to 70°F). They are sensitive to extreme heat and frost.\n\n• Warm climates: In warmer regions, planting potatoes in early spring or late fall can help them avoid the hottest part of the summer.",
            "• Ideal: Tomato plants thrive in warm weather conditions.\n\n• Specifics: The ideal temperature range for growing tomatoes is between 21°C to 29°C (70°F to 85°F). They are sensitive to frost and cold temperatures.\n\n• Nighttime: Nighttime temperatures below 10°C (50°F) can affect fruit set."

        )

        val site = arrayOf(
            "Brassica oleracea var. italica",
            "Brassica oleracea var. capitata",
            "Lactuca sativa",
            "Spinacia oleracea",
            "Fungi",
            "Allium sativum",
            "Allium cepa",
            "• Sunlight: Bell peppers require full sun, at least 6 hours of direct sunlight per day. They will not produce well in partial shade or full shade.\n\n• Soil: Bell peppers prefer well-drained, fertile soil with a pH between 6.0 and 7.0. Amending the soil with compost or aged manure can improve drainage and fertility.",
            "Capsicum frutescens",
            "Daucus carota",
            "Brassica rapa var. rapa",
            "• Sunlight: Potato plants prefer full sun, at least 6 hours of direct sunlight per day. They will produce well in partial sun (3-6 hours of direct sunlight) but may yield slightly less. They will not grow well in full shade.\n\n• Soil: Potatoes thrive in loose, well-drained soil with a slightly acidic pH between 6.0 and 6.8. Sandy loam or loam soil is ideal. Amending the soil with compost or aged manure can improve drainage and fertility.",
            "• Sunlight: Tomato plants require full sun, at least 6 hours of direct sunlight per day. They will not fruit well in partial shade or full shade.\n\n• Soil: Tomatoes prefer well-drained, fertile soil with a pH between 6.0 and 7.0. Sandy loam or loam soil is ideal. Amending the soil with compost or aged manure can improve drainage and fertility."

        )

        userArrayList = ArrayList()

        for (i in name.indices){
            val user = User2(name[i],desc[i],overview[i],pruning[i],watering[i],imageId[i],fertilizing[i],temperature[i],site[i])
            userArrayList.add(user)
        }
        binding.listView.isClickable = true
        binding.listView.adapter = MyAdapter(this, userArrayList)
        binding.listView.setOnItemClickListener{ parent, view, position, id ->
            val name = name[position]
            val desc = desc[position]
            val overview = overview[position]
            val pruning = pruning[position]
            val watering = watering[position]
            val imageId = imageId[position]
            val temperature = temperature[position]
            val fertilizing = fertilizing[position]
            val site = site[position]

            val i = Intent(this, CategoryResult::class.java  )

            i.putExtra("name",name)
            i.putExtra("desc",desc)
            i.putExtra("overview",overview)
            i.putExtra("pruning",pruning)
            i.putExtra("watering",watering)
            i.putExtra("imageId",imageId)
            i.putExtra("temperature",temperature)
            i.putExtra("fertilizing",fertilizing)
            i.putExtra("site",site)
            startActivity(i)

        }

    }

    private fun cancelBtn(){

        binding.cancelTv.setOnClickListener { finish() }

    }

//    private fun search() {
//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }

}