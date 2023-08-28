package com.softstarcompanyltd.starwars.repository.network;


import com.softstarcompanyltd.starwars.repository.dataClasses.people.DataPilot;
import com.softstarcompanyltd.starwars.repository.dataClasses.planet.DataPlanet

import retrofit2.Response
import retrofit2.http.GET;


interface GetAllPlanetService {
    @GET("planets/")
    suspend fun getPlanetList(): Response<DataPlanet>
}
