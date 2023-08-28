package com.softstarcompanyltd.starwars.repository.model;

import android.annotation.SuppressLint
import android.util.Log
import com.softstarcompanyltd.starwars.repository.network.GetAllPeopleService
import com.softstarcompanyltd.starwars.repository.network.GetCurrentFilmService
import com.softstarcompanyltd.starwars.singleton.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetCurrentFilmModel {

    var dataFilmList = mutableListOf<String>()

    @SuppressLint("SuspiciousIndentation")
    suspend fun getFilmData(numberOfFilm: String) {
        val quotesApi = RetrofitClient.getClient().create(GetCurrentFilmService::class.java)
        val dataFilm = quotesApi.getFilmData(numberOfFilm)
        val data = "\n" +
                "Film: ${dataFilm.body()?.title}\n" +
                "Director: ${dataFilm.body()?.director}\n" +
                "Producer: ${dataFilm.body()?.producer}"
        dataFilmList.add(data)
    }


}
