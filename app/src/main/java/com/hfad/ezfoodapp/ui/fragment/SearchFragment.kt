package com.hfad.ezfoodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.ezfoodapp.R
import com.hfad.ezfoodapp.databinding.FragmentSearchBinding
import com.hfad.ezfoodapp.ui.activity.MainActivity
import com.hfad.ezfoodapp.ui.adapter.MealsAdapter
import com.hfad.ezfoodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.searchBtn.setOnClickListener { searchMeals() }
        observeSearchMealLiveData()

        var searchJob : Job? = null
        binding.edSearchBox.addTextChangedListener{ searchQuery->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun observeSearchMealLiveData(){
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer{ mealsList->
            searchAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchAdapter = MealsAdapter()
        binding.rvSearchResult.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchAdapter
        }
    }


}