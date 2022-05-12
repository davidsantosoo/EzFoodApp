package com.hfad.ezfoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.ezfoodapp.pojo.MealByCategory
import com.hfad.ezfoodapp.pojo.MealByCategoryList
import com.hfad.ezfoodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CategoryViewModel:ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealByCategory>>()
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object: retrofit2.Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let { mealByCategoryList ->
                    mealsLiveData.postValue(mealByCategoryList.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("CategoryViewModel",t.message.toString())
            }

        })
    }

    fun observeMealCategoryLivedata():LiveData<List<MealByCategory>>{
        return mealsLiveData
    }
}