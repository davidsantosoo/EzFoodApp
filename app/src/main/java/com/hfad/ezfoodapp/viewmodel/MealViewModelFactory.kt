package com.hfad.ezfoodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.ezfoodapp.db.MealDatabase

class MealViewModelFactory(
    val mealDatabase: MealDatabase
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}