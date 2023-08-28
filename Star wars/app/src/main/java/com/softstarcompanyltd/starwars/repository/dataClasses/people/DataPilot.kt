package com.softstarcompanyltd.starwars.repository.dataClasses.people

data class DataPilot(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)