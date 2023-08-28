package com.softstarcompanyltd.starwars.repository.model;

import com.softstarcompanyltd.starwars.repository.network.GetAllPlanetService
import com.softstarcompanyltd.starwars.singleton.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetAllPlanetModel {

    val listWithPlanetData = ArrayList<String>()

    fun getAllPlanet () {
        val quotesApi = RetrofitClient.getClient().create(GetAllPlanetService::class.java)
        CoroutineScope(Dispatchers.Main).async {
            var data = ""
            val dataPlanet = quotesApi.getPlanetList()
            if (dataPlanet.body().toString().isNotEmpty()) {
                for (i in 0..dataPlanet.body()!!.results.size) {
                    data =  "Type: planet\n" +
                            "Name: ${dataPlanet.body()!!.results[i].name}\n" +
                            "Diameter of planet: ${dataPlanet.body()!!.results[i].diameter}\n" +
                            "Population: ${dataPlanet.body()!!.results[i].population}\n" +
                            "Films: ${dataPlanet.body()!!.results[i].films}\n"
                    listWithPlanetData.add(data)
                }
            }
        }
    }

}
