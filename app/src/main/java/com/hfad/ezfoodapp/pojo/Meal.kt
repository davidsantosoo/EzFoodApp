package com.hfad.ezfoodapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealInfo")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strMeal: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strInstructions: String
)