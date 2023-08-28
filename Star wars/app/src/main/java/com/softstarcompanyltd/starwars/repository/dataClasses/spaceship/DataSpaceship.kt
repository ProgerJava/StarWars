package com.softstarcompanyltd.starwars.repository.dataClasses.spaceship

data class DataSpaceship(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)