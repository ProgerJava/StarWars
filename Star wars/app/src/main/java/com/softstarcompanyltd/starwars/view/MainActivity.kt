package com.softstarcompanyltd.starwars.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.redlanterns.service.MyServiceNetwork
import com.softstarcompanyltd.starwars.R
import com.softstarcompanyltd.starwars.screens.SplashFragment
import com.softstarcompanyltd.starwars.databinding.ActivityMainBinding
import com.softstarcompanyltd.starwars.screens.FavouriteFragment
import com.softstarcompanyltd.starwars.singleton.FAVORITES_FRAGMENT
import com.softstarcompanyltd.starwars.singleton.FIRST_FRAGMENT
import com.softstarcompanyltd.starwars.singleton.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.singleton.SPLASH_FRAGMENT
import com.softstarcompanyltd.starwars.singleton.setObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    ///////////////////FragmentManager
    private lateinit var fragmentManager: FragmentTransaction
    ///////////////////Timer
    private var currentTimer: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        /////////////////Инициализация Singleton objects
        setObject(this@MainActivity)

        replacementFragment(SPLASH_FRAGMENT)

    }

    fun replacementFragment(action: String) {
        ///////////////////инициализация FragmentManager
        fragmentManager = supportFragmentManager.beginTransaction()
        when (action) {
            SPLASH_FRAGMENT -> fragmentManager.add(R.id.frameLayout, SplashFragment()).commit()
            FAVORITES_FRAGMENT -> fragmentManager.replace(R.id.frameLayout, FavouriteFragment()).commit()
            FIRST_FRAGMENT -> fragmentManager.replace(R.id.frameLayout, SplashFragment()).commit()
        }
    }

    override fun onBackPressed() {
        if (currentTimer + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(MAIN_ACTIVITY, "Нажмите еще раз для выхода из приложения", Toast.LENGTH_SHORT).show()
        }
        currentTimer = System.currentTimeMillis()
    }
    override fun onResume() {
        super.onResume()
        ////////////////Мониторинг подключения к сети
        startService(Intent(this, MyServiceNetwork::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        ////////////////Вырубаем сервис
        stopService(Intent(this, MyServiceNetwork::class.java))
        ////////////////Вырубаем БД
        FeedReaderDbHelper.close()
    }
}