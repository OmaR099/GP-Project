package com.example.loginscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.R
import com.example.loginscreen.databinding.MostViewChildBinding
import com.example.loginscreen.databinding.MostViewedCardDesignBinding


class MainAdapter(private val dataItemList: List<DataItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class BannerItemViewHolder(private val binding: MostViewedCardDesignBinding): RecyclerView.ViewHolder(binding.root){

        fun bindBannerView(banner: Banner){
            binding.mvImage.setImageResource(banner.image)
        }
    }

    inner class RecyclerItemViewHolder(private val binding: MostViewChildBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.childRecyclerView.setHasFixedSize(true)
            binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
        }

        fun bindClothingRecyclerView(recyclerItemList: List<RecyclerItem>){

            val adapter = ChildAdapter(DataMostViewed.CLOTHING, recyclerItemList)
            binding.childRecyclerView.adapter = adapter

        }

        fun bindBestSellerRecyclerView(recyclerItemList: List<RecyclerItem>){
            val adapter = ChildAdapter(DataMostViewed.BEST_SELLER, recyclerItemList)
            binding.childRecyclerView.adapter = adapter

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(dataItemList[position].viewType){
            DataMostViewed.BANNER ->
                R.layout.most_viewed_card_design

            else ->
                R.layout.most_view_child
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.most_viewed_card_design ->{
                val binding = MostViewedCardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BannerItemViewHolder(binding)
            }

            else ->{
                val binding = MostViewChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RecyclerItemViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BannerItemViewHolder ->{
                dataItemList[position].banner?.let { holder.bindBannerView(it) }
            }
            else ->{
                when(dataItemList[position].viewType){
                    DataMostViewed.BEST_SELLER ->{
                        dataItemList[position].recyclerItemList?.let {
                            (holder as RecyclerItemViewHolder).bindBestSellerRecyclerView(
                                it
                            )
                        }
                    }
                    else ->{
                        dataItemList[position].recyclerItemList?.let {
                            (holder as RecyclerItemViewHolder).bindClothingRecyclerView(
                                it
                            )
                        }

                    }
                }
            }
        }
    }

}