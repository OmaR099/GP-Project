package com.example.loginscreen.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.R
import com.example.loginscreen.entities.Plants
import com.makeramen.roundedimageview.RoundedImageView
import java.util.*
import kotlin.collections.ArrayList

class PlantsAdapter() :
    RecyclerView.Adapter<PlantsAdapter.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<Plants>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Plants>){
        arrList = arrNotesList as ArrayList<Plants>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {


        holder.tvTitle.text = arrList[position].title
        holder.tvDesc.text = arrList[position].noteText
        holder.tvDateTime.text = arrList[position].dateTime

        if (arrList[position].color != null){
            holder.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))
        }

        if (arrList[position].imgPath != null){
            holder.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.imgNote.visibility = View.VISIBLE
        }else{
            holder.imgNote.visibility = View.GONE
        }

        if (arrList[position].webLink != ""){
            holder.tvWebLink.text = arrList[position].webLink
            holder.tvWebLink.visibility = View.VISIBLE
        }else{
            holder.tvWebLink.visibility = View.GONE
        }

        holder.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }

    }

    class NotesViewHolder(view:View) : RecyclerView.ViewHolder(view){

        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
        val tvWebLink: TextView = view.findViewById(R.id.tvWebLink)
        val cardView: CardView = view.findViewById(R.id.cardView)
        val imgNote: RoundedImageView = view.findViewById(R.id.imgNote)

    }


    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}