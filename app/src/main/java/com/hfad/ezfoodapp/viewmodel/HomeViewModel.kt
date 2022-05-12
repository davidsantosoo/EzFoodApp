package com.hfad.ezfoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.ezfoodapp.db.MealDatabase
import com.hfad.ezfoodapp.pojo.*
import com.hfad.ezfoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (
    private val mealDatabase: MealDatabase
        ): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<MealByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData  = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData  = MutableLiveData<Meal>()
    private var searchMealLiveData  = MutableLiveData<List<Meal>>()


    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment: ", t.message.toString())

            }
        })

    }

    fun searchMeal(searchQuery:String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object: Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        }
    )
     fun getPopularItems() {
        RetrofitInstance.api.getPopularMeal("Seafood")
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularMealLiveData.value = response.body()!!.meals
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {

                    Log.d("HomeFragment: ", t.message.toString())
                }

            })
    }


    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    response.body()?.let { categoryList ->
                        categoriesLiveData.postValue(categoryList.categories)
                    }
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

                Log.d("HomeFragment: ", t.message.toString())
            }

        })
    }
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object: Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let { meal ->  
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.e("HomeViewModel",t.message.toString())

            }

        })
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<MealByCategory>> {
        return popularMealLiveData

    }

    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoriesLiveData

    }

    fun observerFavoriteMealLiveData(): LiveData<List<Meal>>{
        return favoriteMealLiveData
    }
    fun observeBottomSheetMealLiveData(): LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    fun observeSearchMealLiveData(): LiveData<List<Meal>> = searchMealLiveData

}