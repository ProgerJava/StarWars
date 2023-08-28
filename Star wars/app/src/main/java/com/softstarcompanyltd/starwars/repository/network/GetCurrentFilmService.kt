package com.softstarcompanyltd.starwars.repository.network;


import com.softstarcompanyltd.starwars.repository.dataClasses.films.DataFilms

import retrofit2.Response
import retrofit2.http.GET;
import retrofit2.http.Path


interface GetCurrentFilmService {
    @GET("{path}")
    suspend fun getFilmData (@Path("path") filmId: String): Response<DataFilms>
}
