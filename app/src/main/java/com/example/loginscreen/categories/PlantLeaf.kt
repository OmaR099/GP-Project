package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2

class PlantLeaf: AppCompatActivity() {
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

            R.drawable.corn_plant_dracaena_fragrans,
            R.drawable.heartleaf_philodendron,
            R.drawable.golden_pothos,
            R.drawable.baby_rubber_plant,
            R.drawable.snake_plant,
            R.drawable.english_ivy,
            R.drawable.garden_croton,
            R.drawable.dumbcane
        )

        val name = arrayOf(
            "Corn plant",
            "Heartleaf philodendron",
            "Golden pothos",
            "Baby rubber plant",
            "Snake plant",
            "English ivy",
            "Garden croton",
            "Dumbcane"
        )

        val desc = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val overview = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val pruning = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val watering = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val fertilizing = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val temperature = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
        )

        val site = arrayOf(
            "Dracaena fragrans",
            "Philodendron hederaceum",
            "Epipremnum aureum",
            "Peperomia obtusifolia",
            "Sansevieria trifasciata",
            "Hedera helix",
            "Codiaeum variegatum",
            "Dieffenbachia seguine"
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