package com.hfad.ezfoodapp.retrofit

import com.hfad.ezfoodapp.pojo.CategoryList
import com.hfad.ezfoodapp.pojo.MealByCategoryList
import com.hfad.ezfoodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(@Query("c") categoryName: String): Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String): Call<MealList>
}