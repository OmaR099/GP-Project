package com.example.loginscreen.myPlants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.loginscreen.BaseFragment
import com.example.loginscreen.CreatePlantFragment
import com.example.loginscreen.R
import com.example.loginscreen.adapter.PlantsAdapter
import com.example.loginscreen.database.PlantDatabase
import com.example.loginscreen.entities.Plants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : BaseFragment() {

    var arrNotes = ArrayList<Plants>()
    var notesAdapter: PlantsAdapter = PlantsAdapter()
    private lateinit var recycler_view: RecyclerView
    private lateinit var search_view: SearchView
    private lateinit var fabBtnCreateNote: FloatingActionButton
    private lateinit var iv_setting: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_create_plant_home, container, false)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view = view.findViewById(R.id.recycler_view)
        search_view = view.findViewById(R.id.search_view)
        fabBtnCreateNote = view.findViewById(R.id.fabBtnCreateNote)
        iv_setting = view.findViewById(R.id.iv_setting)

        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = PlantDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter.setData(notes)
                arrNotes = notes as ArrayList<Plants>
                recycler_view.adapter = notesAdapter
            }
        }

        notesAdapter.setOnClickListener(onClicked)

        fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreatePlantFragment.newInstance(),false)
        }

        iv_setting.setOnClickListener {
            startMyActivity()
        }

        search_view.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                val tempArr = ArrayList<Plants>()

                for (arr in arrNotes){
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })


    }


    private val onClicked = object :PlantsAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {


            val fragment :Fragment
            val bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment = CreatePlantFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)
        }

    }


    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    fun startMyActivity() {
        val intent = Intent(requireActivity(), PlantSettings::class.java)
        startActivity(intent)
    }

}