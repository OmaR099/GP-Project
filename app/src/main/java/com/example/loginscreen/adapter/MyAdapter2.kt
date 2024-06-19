package com.example.loginscreen.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.loginscreen.R
import com.example.loginscreen.home.User2
import com.example.loginscreen.home.User3

class MyAdapter2(private val context: Activity, private val arrayList: ArrayList<User3>) : ArrayAdapter<User3>(context, R.layout.list_diseases,arrayList)
 {
     @SuppressLint("ViewHolder")
     override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

         val inflater: LayoutInflater = LayoutInflater.from(context)
         val view: View = inflater.inflate(R.layout.list_diseases, null)

         val imageViewList: ImageView = view.findViewById(R.id.imageViewList)
         val fruitName: TextView = view.findViewById(R.id.disease_dec)

         imageViewList.setImageResource(arrayList[position].imageId)
         fruitName.text = arrayList[position].overview

         return view
     }
}