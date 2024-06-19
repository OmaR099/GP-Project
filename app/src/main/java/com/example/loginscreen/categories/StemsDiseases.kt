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

class StemsDiseases: AppCompatActivity() {
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
            "The stems are wilting and drooping over.",
            "The roots have turned soft and are starting to rot.",
            "Mold or mushrooms are forming on the soil's surface.",
            "There is a rough, tumor-like growth called a gall forming on the stems. it will be black or brown.",
            "The roots are deformed.",
            "Dense shoots and foliage are growing out from a single point on a stem or branch.",
            "Brown spots have appeared on young stems.",
            "These are soft, white powdery bumps, about 1-2 mm in size."
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
            "Wilting isn't actually a disease, but a sign that something is wrong with your flowers. There are a few reasons why this might happen:",
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
            "Echinacea purpurea",
            "Hemerocallis spp.",
            "Digitalis purpurea",
            "Hosta spp.",
            "Lavandula angustifolia",
            "Dianthus barbatus",
            "Zinnia elegans"
        )

        val watering = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Hemerocallis spp.",
            "Digitalis purpurea",
            "Hosta spp.",
            "Lavandula angustifolia",
            "Dianthus barbatus",
            "Zinnia elegans"
        )

        val fertilizing = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Hemerocallis spp.",
            "Digitalis purpurea",
            "Hosta spp.",
            "Lavandula angustifolia",
            "Dianthus barbatus",
            "Zinnia elegans"
        )

        val temperature = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Hemerocallis spp.",
            "Digitalis purpurea",
            "Hosta spp.",
            "Lavandula angustifolia",
            "Dianthus barbatus",
            "Zinnia elegans"
        )

        val site = arrayOf(
            "Impatiens balsamina",
            "Tagetes spp.",
            "Petunia x hybrida",
            "Rudbeckia fulgida",
            "Hibiscus rosa-sinensis",
            "Echinacea purpurea",
            "Hemerocallis spp.",
            "Digitalis purpurea",
            "Hosta spp.",
            "Lavandula angustifolia",
            "Dianthus barbatus",
            "Zinnia elegans"
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