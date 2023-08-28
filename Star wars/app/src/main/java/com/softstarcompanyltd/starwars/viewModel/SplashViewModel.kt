package com.softstarcompanyltd.starwars.viewModel

import androidx.lifecycle.ViewModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPeopleModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPlanetModel
import com.softstarcompanyltd.starwars.repository.model.GetAllSpaceshipModel
import com.softstarcompanyltd.starwars.singleton.getAllPeopleModel
import com.softstarcompanyltd.starwars.singleton.getAllPlanetModel
import com.softstarcompanyltd.starwars.singleton.getAllSpaceshipModel

import kotlinx.coroutines.delay

class SplashViewModel : ViewModel() {

    suspend fun checkContainsChars(ch: String): ArrayList<String> {
        /////////////Список соответствий
        var listWithContains = ArrayList<String>()
        /////////////////Список людей
        val listWithPeopleData = getAllPeopleModel.listWithPeopleData
        /////////////////Список кораблей
        val listWithShipData = getAllSpaceshipModel.listWithShipData
        /////////////////Список планет
        val listWithPlanetData = getAllPlanetModel.listWithPlanetData

        while (listWithPeopleData.size == 0 && listWithShipData.size == 0) {
            delay(500)
        }
        ////////////////Создаем общий список с людьми/кораблями/планетами
        val list = ArrayList <String> (listWithPeopleData)
        list.addAll(listWithShipData)
        list.addAll(listWithPlanetData)

        for (i in 0 until list.size) {
            if (list[i].toLowerCase().contains(ch.toLowerCase()) && !listWithContains.contains(list[i])) {
                listWithContains.add(list[i])
            }
        }
        return listWithContains
    }

}