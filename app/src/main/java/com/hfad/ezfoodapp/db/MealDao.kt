package com.hfad.ezfoodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hfad.ezfoodapp.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsert(meal: Meal)

    @Delete
     suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInfo")
    fun getAllMeals(): LiveData<List<Meal>>
}