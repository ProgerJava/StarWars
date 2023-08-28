package com.softstarcompanyltd.starwars.repository.network;


import com.softstarcompanyltd.starwars.repository.dataClasses.people.DataPilot;

import retrofit2.Response
import retrofit2.http.GET;


interface GetAllPeopleService {
    @GET("people/")
    suspend fun getPeopleList(): Response<DataPilot>
}
