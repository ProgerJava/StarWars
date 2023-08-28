package com.softstarcompanyltd.starwars.repository.network;


import com.softstarcompanyltd.starwars.repository.dataClasses.spaceship.DataSpaceship

import retrofit2.Response
import retrofit2.http.GET;


interface GetAllSpaceshipService {
    @GET("starships/")
    suspend fun getSpaceshipList(): Response<DataSpaceship>
}
