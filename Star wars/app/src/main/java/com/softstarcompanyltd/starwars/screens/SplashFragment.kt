package com.softstarcompanyltd.starwars.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softstarcompanyltd.starwars.R
import com.softstarcompanyltd.starwars.components.recyclers.RecyclerViewAdapterSplashScreen
import com.softstarcompanyltd.starwars.databinding.FragmentSplashBinding
import com.softstarcompanyltd.starwars.repository.model.GetAllPeopleModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPlanetModel
import com.softstarcompanyltd.starwars.repository.model.GetAllSpaceshipModel
import com.softstarcompanyltd.starwars.singleton.FAVORITES_FRAGMENT
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.viewModel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var animationComet1 : Animation
    private lateinit var animationAppName : Animation
    private lateinit var animationBack : Animation

    ////////////////////ViewModel
    private lateinit var splashViewModel: SplashViewModel
    ////////////////////DataBinding
    private lateinit var splashBinding: FragmentSplashBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashBinding =  FragmentSplashBinding.inflate(inflater, container, false)
        ////////////////////Анимация
        animationComet1 = AnimationUtils.loadAnimation(MAIN_ACTIVITY, R.anim.anim_comet1)
        animationAppName = AnimationUtils.loadAnimation(MAIN_ACTIVITY, R.anim.anim_app_name)
        animationBack = AnimationUtils.loadAnimation(MAIN_ACTIVITY, R.anim.anim_back)

        /////////////////Инициализация VM
        splashViewModel = ViewModelProvider (this)[SplashViewModel::class.java]
        /////////////////Инициализация RV
        splashBinding.recyclerView.layoutManager = LinearLayoutManager(MAIN_ACTIVITY, RecyclerView.VERTICAL, false)


        /////////////////////Слушатель изменений набранного текста + кнопка Done
        splashBinding.searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true && query.length >= 2) {
                    splashBinding.recyclerView.visibility = View.VISIBLE
                    val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken,0)
                    splashBinding.searchView.clearFocus()
                }else {
                    splashBinding.recyclerView.visibility = View.INVISIBLE
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true && newText.length >= 2) {
                    splashBinding.recyclerView.visibility = View.VISIBLE
                    CoroutineScope(Dispatchers.Main).launch {
                        val list = splashViewModel.checkContainsChars(newText)
                        splashBinding.recyclerView.adapter = RecyclerViewAdapterSplashScreen(list, splashBinding)
                        splashBinding.recyclerView.adapter?.notifyDataSetChanged()
                    }
                } else {
                    splashBinding.recyclerView.visibility = View.INVISIBLE
                }
                return true
            }
        })
        splashBinding.favourite.setOnClickListener {
            MAIN_ACTIVITY.replacementFragment(FAVORITES_FRAGMENT)
        }



        return splashBinding.root
    }

    override fun onResume() {
        super.onResume()
        ///////////////////Разовая анимашка при загрузке
        splashBinding.comet1.visibility = View.VISIBLE
        splashBinding.comet1.startAnimation(animationComet1)
        splashBinding.nameApp.startAnimation(animationAppName)
        splashBinding.back.startAnimation(animationBack)
        splashBinding.comet1.visibility = View.INVISIBLE
    }

}