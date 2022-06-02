package com.example.tracecovid

data class CovidData(
    val testedPositive: Int,
    val activeCases: Int,
    val decreased: Int,
    val recovered: Int,
    val critical: Int,
    val lastUpdatedAtApify: String
)
