package com.hfad.ezfoodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.ezfoodapp.databinding.PopularItemBinding
import com.hfad.ezfoodapp.pojo.MealByCategory
import kotlin.collections.ArrayList

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>() {
    lateinit var onItemClick:((MealByCategory)->Unit)
     var onLongItemClickListener:((MealByCategory)->Unit)?=null
    private var mealsList = ArrayList<MealByCategory>()

    fun setMeals(mealsList: ArrayList<MealByCategory>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
    class PopularViewHolder( var binding:PopularItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.ivPopular)

        holder.itemView.setOnClickListener {
          onItemClick.invoke(mealsList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClickListener?.invoke(mealsList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}