package com.hfad.ezfoodapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hfad.ezfoodapp.databinding.ActivityCategoryMealsBinding
import com.hfad.ezfoodapp.ui.adapter.CategoryMealsAdapter
import com.hfad.ezfoodapp.ui.fragment.HomeFragment
import com.hfad.ezfoodapp.viewmodel.CategoryViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var viewModel :CategoryViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMealCategory.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
        viewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        viewModel.observeMealCategoryLivedata().observe(this, Observer { mealList->
           binding.tvCategoryCount.text = "Total Item : ${mealList.size}"
            categoryMealsAdapter.setMealList(mealList)
        })


    }


}