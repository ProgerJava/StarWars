package com.softstarcompanyltd.starwars.repository.dataClasses.planet

data class DataPlanet(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)