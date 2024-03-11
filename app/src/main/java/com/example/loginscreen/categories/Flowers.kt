package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2

class Flowers: AppCompatActivity() {
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

            R.drawable.impatiens,
            R.drawable.marigolds,
            R.drawable.petunias,
            R.drawable.black_eyed_susans,
            R.drawable.china_rose,
            R.drawable.coneflowers,
            R.drawable.daylilies,
            R.drawable.foxgloves,
            R.drawable.hostas,
            R.drawable.lavender,
            R.drawable.sweet_william,
            R.drawable.zinnias
        )

        val name = arrayOf(
            "Impatiens",
            "Marigolds",
            "Petunias",
            "Black-eyed Susans",
            "China Rose",
            "Coneflowers",
            "Daylilies",
            "Foxgloves",
            "Hostas",
            "Lavender",
            "Sweet William",
            "Zinnias"
        )

        val desc = arrayOf(
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

        val overview = arrayOf(
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