package com.hfad.ezfoodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.ezfoodapp.databinding.MealItemBinding
import com.hfad.ezfoodapp.pojo.Meal
import com.hfad.ezfoodapp.pojo.MealByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var mealsList = ArrayList<MealByCategory>()
    fun setMealList(mealsList: List<MealByCategory>) {
        this.mealsList = mealsList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.ivMeal)

        holder.binding.tvMealItem.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}