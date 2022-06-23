package com.example.tracecovid.data

data class InternationalCovidData(
    val updated: String,
    val cases: Int,
    val todayCases: Int,
    val deaths: Int,
    val todayDeaths: Int,
    val recovered: Int,
    val todayRecovered: Int,
    val active: Int,
    val critical: Int
)
