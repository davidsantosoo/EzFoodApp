package com.hfad.ezfoodapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hfad.ezfoodapp.databinding.FragmentFavoriteBinding
import com.hfad.ezfoodapp.ui.activity.MainActivity
import com.hfad.ezfoodapp.ui.adapter.MealsAdapter
import com.hfad.ezfoodapp.viewmodel.HomeViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRvFavorite()
        observerFavoriteLiveData()
        val itemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position  = viewHolder.adapterPosition
                viewModel.deleteMeal(mealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal Deleted Successfully",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(mealsAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFav)
    }

    private fun prepareRvFavorite() {
        mealsAdapter = MealsAdapter()
        binding.rvFav.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

    private fun observerFavoriteLiveData() {
        viewModel.observerFavoriteMealLiveData().observe(requireActivity(), Observer { meals ->
            mealsAdapter.differ.submitList(meals)
        })
    }

}