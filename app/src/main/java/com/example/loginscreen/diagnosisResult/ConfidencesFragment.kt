package com.example.loginscreen.diagnosisResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loginscreen.R
import com.example.loginscreen.databinding.FragmentConfidencesBinding

class ConfidencesFragment : Fragment() {
    private lateinit var binding: FragmentConfidencesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_confidences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.value_tv)
        val result = arguments?.getString("confidence_holder")
        textView.text = result.toString()
    }
}