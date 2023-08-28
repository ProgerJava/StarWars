package com.softstarcompanyltd.starwars.singleton

import android.annotation.SuppressLint
import android.app.Activity
import com.softstarcompanyltd.starwars.repository.DB.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.repository.model.GetAllPeopleModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPlanetModel
import com.softstarcompanyltd.starwars.repository.model.GetAllSpaceshipModel
import com.softstarcompanyltd.starwars.repository.model.GetCurrentFilmModel
import com.softstarcompanyltd.starwars.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
lateinit var MAIN_ACTIVITY : MainActivity
////////////////////Запрос, получение всех пользователей
lateinit var getAllPeopleModel: GetAllPeopleModel
////////////////////Запрос, получение всех пользователей
lateinit var getAllSpaceshipModel: GetAllSpaceshipModel
////////////////////Запрос, получение всех планет
lateinit var getAllPlanetModel: GetAllPlanetModel
////////////////////Запрос, получение данных на фильм
lateinit var getCurrentFilmModel: GetCurrentFilmModel
////////////////////Запросы к БД
val FeedReaderDbHelper : FeedReaderDbHelper by lazy (::getFeedReaderInstance)



fun setObject (activity: Activity) {
    MAIN_ACTIVITY = activity as MainActivity
    CoroutineScope(Dispatchers.Main).launch {
        /////////////////Получаем список всех имен
        getAllPeopleModel = GetAllPeopleModel()
        getAllPeopleModel.getAllPeople()
        /////////////////Получаем список всех космических кораблей
        getAllSpaceshipModel = GetAllSpaceshipModel()
        getAllSpaceshipModel.getAllSpaceship()
        /////////////////Получаем список всех планет
        getAllPlanetModel = GetAllPlanetModel()
        getAllPlanetModel.getAllPlanet()
        /////////////////
        getCurrentFilmModel = GetCurrentFilmModel()
        /////////////////
    }
}
fun getFeedReaderInstance () : FeedReaderDbHelper {
    return FeedReaderDbHelper(MAIN_ACTIVITY)
}

const val SPLASH_FRAGMENT = "SplashFragment"
const val FAVORITES_FRAGMENT = "FavouriteFragment"
const val FIRST_FRAGMENT = "FirstFragment"





object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}