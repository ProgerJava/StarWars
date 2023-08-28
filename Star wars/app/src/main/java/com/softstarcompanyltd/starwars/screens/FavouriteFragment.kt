package com.softstarcompanyltd.starwars.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softstarcompanyltd.starwars.components.recyclers.RecyclerViewAdapterFavouriteScreen
import com.softstarcompanyltd.starwars.databinding.FragmentFavouriteBinding
import com.softstarcompanyltd.starwars.singleton.FIRST_FRAGMENT
import com.softstarcompanyltd.starwars.singleton.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.viewModel.FavouriteViewModel


class FavouriteFragment : Fragment() {

    private lateinit var favouriteBinding: FragmentFavouriteBinding
    ///////////////////VM
    private lateinit var favouriteViewModel: FavouriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favouriteBinding =  FragmentFavouriteBinding.inflate(inflater, container, false)
        favouriteViewModel = ViewModelProvider (MAIN_ACTIVITY)[FavouriteViewModel::class.java]

        favouriteBinding.back.setOnClickListener {
            MAIN_ACTIVITY.replacementFragment(FIRST_FRAGMENT)
        }

        /////////////////Инициализация RV
        favouriteBinding.recyclerView.layoutManager = LinearLayoutManager(MAIN_ACTIVITY, RecyclerView.HORIZONTAL, false)
        ////////////////Получаем из БД сохраненки
        favouriteViewModel.getDataFromDatabase()


        favouriteViewModel.arrayWithDataEntity.observe(MAIN_ACTIVITY) {
            favouriteBinding.recyclerView.adapter = RecyclerViewAdapterFavouriteScreen(it, favouriteViewModel)
            favouriteBinding.recyclerView.adapter?.notifyDataSetChanged()
        }

        return favouriteBinding.root
    }
}