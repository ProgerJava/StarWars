package com.softstarcompanyltd.starwars.repository.model;

import android.annotation.SuppressLint
import android.util.Log
import com.softstarcompanyltd.starwars.repository.network.GetAllPeopleService
import com.softstarcompanyltd.starwars.singleton.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetAllPeopleModel {

    val listWithPeopleData = mutableListOf<String>()

    @SuppressLint("SuspiciousIndentation")
    fun getAllPeople () {
        val quotesApi = RetrofitClient.getClient().create(GetAllPeopleService::class.java)
        CoroutineScope(Dispatchers.Main).async {
            var data = ""
            val dataPilot = quotesApi.getPeopleList()
            if (dataPilot.body().toString().isNotEmpty()) {
                for (i in 0..dataPilot.body()!!.results.size) {
                    data =  "Type: people\n" +
                            "Name: ${dataPilot.body()!!.results[i].name}\n" +
                            "Gender: ${dataPilot.body()!!.results[i].gender}\n" +
                            "Number of starships: ${dataPilot.body()!!.results[i].starships.size}\n" +
                            "Films: ${dataPilot.body()!!.results[i].films}\n"
                    Log.println(Log.INFO, "name", data)
                    listWithPeopleData.add(data)
                }
            }
        }
    }

}
