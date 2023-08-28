package com.softstarcompanyltd.starwars.repository.model;

import android.util.Log
import com.softstarcompanyltd.starwars.repository.network.GetAllPeopleService
import com.softstarcompanyltd.starwars.repository.network.GetAllSpaceshipService
import com.softstarcompanyltd.starwars.singleton.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetAllSpaceshipModel {

    val listWithShipData = mutableListOf<String>()

    fun getAllSpaceship () {
        val quotesApi = RetrofitClient.getClient().create(GetAllSpaceshipService::class.java)
        CoroutineScope(Dispatchers.Main).async {
            var data = ""
            val dataSpaceship = quotesApi.getSpaceshipList()
            if (dataSpaceship.body().toString().isNotEmpty()) {
                for (i in 0..dataSpaceship.body()!!.results.size) {
                    data =  "Type: spaceship\n" +
                            "Name: ${dataSpaceship.body()!!.results[i].name}\n" +
                            "Model: ${dataSpaceship.body()!!.results[i].model}\n" +
                            "Manufacturer: ${dataSpaceship.body()!!.results[i].manufacturer}\n" +
                            "Passengers: ${dataSpaceship.body()!!.results[i].passengers}\n" +
                            "Films: ${dataSpaceship.body()!!.results[i].films}\n"

                    Log.println(Log.INFO, "name", data)
                    listWithShipData.add(data)
                }
            }
        }
    }

}
