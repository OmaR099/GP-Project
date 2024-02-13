package com.example.loginscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.databinding.FeaturedCardDesignBinding
import com.example.loginscreen.databinding.FeaturedChildBinding

class ChildAdapter(private val viewType: Int, private val recyclerItemList: List<RecyclerItem>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class BestSellerViewHolder(private val binding: FeaturedCardDesignBinding): RecyclerView.ViewHolder(binding.root){

        fun bindBestSellerView(recyclerItem: RecyclerItem){
            binding.featuredImage.setImageResource(recyclerItem.image)
            binding.featuredTitle.text= recyclerItem.offer

        }


    }

    inner class ClothingViewHolder( private val binding: FeaturedChildBinding): RecyclerView.ViewHolder(binding.root){

        fun bindClothingView(recyclerItem: RecyclerItem){
            binding.featuredChildImage.setImageResource(recyclerItem.image)
            binding.featuredChildTitle.text= recyclerItem.offer

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            DataMostViewed.BEST_SELLER ->{
                val binding = FeaturedCardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BestSellerViewHolder(binding)
            }

            else ->{
                val binding = FeaturedChildBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ClothingViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BestSellerViewHolder ->{
                holder.bindBestSellerView(recyclerItemList[position])
            }
            is ClothingViewHolder ->{
                holder.bindClothingView(recyclerItemList[position])
            }
        }

    }
}