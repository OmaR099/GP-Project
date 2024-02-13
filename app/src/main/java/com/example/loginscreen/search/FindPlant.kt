package com.example.loginscreen.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loginscreen.R

class FindPlant : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_find_plant, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the button in the layout
        val searchButton: TextView = view.findViewById(R.id.search_et)

        // Set OnClickListener for the button
        searchButton.setOnClickListener {
            // Close the fragment
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

}