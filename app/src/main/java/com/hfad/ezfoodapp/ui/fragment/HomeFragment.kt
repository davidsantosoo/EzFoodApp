package com.hfad.ezfoodapp.ui.fragment

import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hfad.ezfoodapp.R
import com.hfad.ezfoodapp.databinding.FragmentHomeBinding
import com.hfad.ezfoodapp.pojo.MealByCategory
import com.hfad.ezfoodapp.pojo.Meal
import com.hfad.ezfoodapp.ui.activity.CategoryMealsActivity
import com.hfad.ezfoodapp.ui.activity.MainActivity
import com.hfad.ezfoodapp.ui.activity.MealActivity
import com.hfad.ezfoodapp.ui.adapter.CategoryAdapter
import com.hfad.ezfoodapp.ui.adapter.MostPopularAdapter
import com.hfad.ezfoodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter



    companion object{
        const val MEAL_ID = "com.hfad.ezfoodapp.ui.fragment.mealId"
        const val MEAL_NAME="com.hfad.ezfoodapp.ui.fragment.mealName"
        const val MEAL_THUMB="com.hfad.ezfoodapp.ui.fragment.mealThumb"


        const val CATEGORY_NAME = "com.hfad.ezfoodapp.ui.fragment.categoryName"



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularAdapter = MostPopularAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRvPopular()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observerPopularLiveData()
        onPopularItemClick()

        prepareRvCategory()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryItemClick()

        onPopularLongItemClick()

        onSearchItemClick()

    }

    private fun onSearchItemClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularLongItemClick() {
        popularAdapter.onLongItemClickListener = {mealByCategory ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(mealByCategory.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info    ")
        }
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRvCategory() {
        binding.rv2.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->

               categoryAdapter.setCategory(categories)

        })
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun prepareRvPopular() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularAdapter
        }
    }

    private fun observerPopularLiveData() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            popularAdapter.setMeals(mealsList = meal as ArrayList<MealByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.cvRandom1.setOnClickListener {
            val intent  =Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.ivRandom)

            this.randomMeal = meal
        }
    }

}