package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.adapter.MyAdapter2
import com.example.loginscreen.databinding.DiseasesCategoryBinding
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2
import com.example.loginscreen.home.User3

class FlowersDiseases: AppCompatActivity() {
    private lateinit var binding: DiseasesCategoryBinding
    private lateinit var userArrayList: ArrayList<User3>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiseasesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        callbacks
//        search()
        cancelBtn()

        val imageId = intArrayOf(

            R.drawable.flower_wilted,
            R.drawable.flower_small_insects,
            R.drawable.flower_turned,
            R.drawable.flower_dried,
            R.drawable.flower_beetles,
            R.drawable.flower_insects
        )

        val name = arrayOf(
            "Flower Wilting",
            "Leaf Weevils",
            "Flower Rot",
            "Flower Withering",
            "Leaf Beetles",
            "Caterpillars"
        )

        val desc = arrayOf(
            "The flowers are wilted.",
            "These small to medium-sized insects have rounded, heavily armored bodies. most are dark-colored.",
            "The flowers have turned mushy and rotten.",
            "The flowers are dried out.",
            "These are beetles that come in a variety of colors and range in size from 1 to 20 mm , most have an oval bodies.",
            "These are insects with slender (almost wormlike) bodies and short legs."
        )

        val overview = arrayOf(
            "The flowers are wilted.",
            "These small to medium-sized insects have rounded, heavily armored bodies. most are dark-colored.",
            "The flowers have turned mushy and rotten.",
            "The flowers are dried out.",
            "These are beetles that come in a variety of colors and range in size from 1 to 20 mm , most have an oval bodies.",
            "These are insects with slender (almost wormlike) bodies and short legs."
        )

        val pruning = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea"
        )

        val watering = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Your plant’s leaves are missing irregular sections. Some leaves may be stripped down to nothing but the veins! You may also see oblong, slow-moving, bugs on your plants. These bugs are caterpillars.\n\nAlthough butterflies and moths are important pollinators, their larvae, known as caterpillars, can cause significant damage to plants when present in large numbers. Caterpillars are very diverse; they range from 1/8th of an inch to over half a foot in length, and they are a variety of colors and patterns. Different caterpillar species also feed on different species of plants.\n\nMost caterpillars are common in the spring and summer although some do live during the fall and winter."
        )

        val fertilizing = arrayOf(
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Even though caterpillars are diverse, they all chew on plant parts and can cause significant damage if present in large numbers.\n\nFor severe cases:\n\n1. Apply insecticide. For an organic solution, spray your plants with a Bacillus thuringiensis (Bt), a bacteria that specifically affects the larval stage of moths and butterflies. Be sure to coat your plants, since caterpillars need to ingest Bt for it to be effective. But will not harm other insects.\n2. Spray a chili extract. Chili seeds can be cooked in water to make a spicy spray that caterpillars don’t like. Spray this mixture on your plants, but be aware it will also be spicy to humans.\n3. Introduce beneficial insects. Release beneficial insects to your garden that eat caterpillars, such as parasitic wasps.\n\nFor less severe cases:\n\n4. Hand pick. Using gloves, pick off caterpillars on plants and dispose of them in a bucket of soapy water.\n5. Dust plants with diatomaceous earth. This powder is harmless to humans, but irritates caterpillars. Therefore, it will make it difficult for caterpillars to move and eat."
        )

        val temperature = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "It might be a shame to eliminate caterpillars that belong to beneficial butterflies, so it would be best to prevent them instead. Here are our top steps for prevention:\n\n1. Monitor plants. Check plants regularly for caterpillar eggs on leaves. If they do not belong to an endangered species, they should be squished.\n2. Use insect netting. Cover plants with insect netting to prevent butterflies and moths from laying eggs on your plants.\n3. Apply diatomaceous earth. Apply DE to your plants early in the season and reapply after rain.\n4. Encourage plant diversity. This will attract predatory insects including parasitic wasps."
        )

        val site = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Hemerocallis spp."
        )

        userArrayList = ArrayList()

        for (i in name.indices){
            val user = User3(name[i],desc[i],overview[i],pruning[i],watering[i],imageId[i],fertilizing[i],temperature[i],site[i])
            userArrayList.add(user)
        }
        binding.listView.isClickable = true
        binding.listView.adapter = MyAdapter2(this, userArrayList)
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

            val i = Intent(this, CategoryDiseaseResult::class.java  )

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