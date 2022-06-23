package com.example.tracecovid.data

data class CovidData(
    val testedPositive: Int,
    val activeCases: Int,
    val deceased: Int,
    val recovered: Int,
    val tested: Int,
    val critical: Int,
    val lastUpdatedAtApify: String
)
