package com.example.loginscreen.categories

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.R
import com.example.loginscreen.adapter.MyAdapter
import com.example.loginscreen.databinding.FruitsCategoryBinding
import com.example.loginscreen.home.User2

class Trees: AppCompatActivity() {
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

            R.drawable.weeping_fig,
            R.drawable.japanese_privet,
            R.drawable.cherry_plums,
            R.drawable.cornus_florida_arkansas,
            R.drawable.rubber_plant,
            R.drawable.curtain_fig,
            R.drawable.japanese_maple,
            R.drawable.black_walnut
        )

        val name = arrayOf(
            "Weeping fig",
            "Japanese privet",
            "Cherry plum",
            "Flowering dogwood",
            "Rubber plant",
            "Curtain fig",
            "Japanese maple",
            "Black walnut"
        )

        val desc = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val overview = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val pruning = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val watering = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val fertilizing = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val temperature = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
        )

        val site = arrayOf(
            "Ficus benjamina",
            "Ligustrum japonicum",
            "Prunus cerasifera",
            "Cornus florida",
            "Ficus elastica",
            "Ficus microcarpa",
            "Acer palmatum",
            "Juglans nigra"
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