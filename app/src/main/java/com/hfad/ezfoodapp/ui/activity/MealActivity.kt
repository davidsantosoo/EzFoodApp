package com.hfad.ezfoodapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.hfad.ezfoodapp.R
import com.hfad.ezfoodapp.databinding.ActivityMealBinding
import com.hfad.ezfoodapp.db.MealDatabase
import com.hfad.ezfoodapp.pojo.Meal
import com.hfad.ezfoodapp.ui.fragment.HomeFragment
import com.hfad.ezfoodapp.viewmodel.MealViewModel
import com.hfad.ezfoodapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""
    private var ytUrl = ""
    private lateinit var viewModel: MealViewModel
    private lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]


        getInfoFromIntent()
        setInfoFromIntent()
        loadingCase()
        viewModel.getMealDetail(mealId)
        observeMealDetailLiveData()
        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.fabBtn.setOnClickListener {
            mealToSave?.let {
                viewModel.insertMeal(it)
                Toast.makeText(this, "Meal Has been Saved", Toast.LENGTH_SHORT).show()
                binding.fabBtn.setImageResource(R.drawable.ic_favorite_fill)
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.cvYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl))
            startActivity(intent)
        }
    }
    private var mealToSave:Meal? =null
    private fun observeMealDetailLiveData() {
        viewModel.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            @SuppressLint("SetTextI18n")
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal
                binding.tvCategoryh.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvDesc.text = meal.strInstructions
                ytUrl = meal.strYoutube!!
            }
        })
    }

    private fun setInfoFromIntent() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)


        binding.collapsingToolbar.title = mealStr
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))

    }

    private fun getInfoFromIntent() {
        val intent = intent
        this.mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        this.mealStr = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        this.mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!


    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.fabBtn.visibility = View.INVISIBLE
        binding.tvDesc.visibility = View.INVISIBLE
        binding.tvCategoryh.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.cvYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.fabBtn.visibility = View.VISIBLE
        binding.tvDesc.visibility = View.VISIBLE
        binding.tvCategoryh.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.cvYoutube.visibility = View.VISIBLE
    }
}