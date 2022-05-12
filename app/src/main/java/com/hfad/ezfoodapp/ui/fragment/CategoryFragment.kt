package com.hfad.ezfoodapp.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hfad.ezfoodapp.R
import com.hfad.ezfoodapp.databinding.FragmentCategoryBinding
import com.hfad.ezfoodapp.ui.activity.CategoryMealsActivity
import com.hfad.ezfoodapp.ui.activity.MainActivity
import com.hfad.ezfoodapp.ui.adapter.CategoryAdapter
import com.hfad.ezfoodapp.viewmodel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel  = (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecylerViewFav()

        observeCategory()
        onCategoryItemClick()
    }

    private fun observeCategory() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner,{ categories->
            categoryAdapter.setCategory(categories)
        })
    }

    private fun prepareRecylerViewFav() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategoryFragment.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }
    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

}