package com.example.loginscreen.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.loginscreen.R

class LightTipsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tips_light_meter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val closeBtn = view.findViewById<TextView>(R.id.light_tips_close)

        closeBtn.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }
    }

}