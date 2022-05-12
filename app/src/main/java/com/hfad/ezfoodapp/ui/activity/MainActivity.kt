package com.hfad.ezfoodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hfad.ezfoodapp.R
import com.hfad.ezfoodapp.db.MealDatabase
import com.hfad.ezfoodapp.viewmodel.HomeViewModel
import com.hfad.ezfoodapp.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by lazy{
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigationBar, navController)
    }
}