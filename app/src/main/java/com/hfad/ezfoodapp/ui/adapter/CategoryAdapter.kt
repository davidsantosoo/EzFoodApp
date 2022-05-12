package com.hfad.ezfoodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.ezfoodapp.databinding.CategoryItemsBinding
import com.hfad.ezfoodapp.pojo.Category

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<Category>()
     var onItemClick: ((Category)->Unit)?=null

    fun setCategory(categoryList: List<Category>) {
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(var binding: CategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}