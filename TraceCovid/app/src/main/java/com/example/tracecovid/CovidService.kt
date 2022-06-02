package com.example.tracecovid

import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("items?format=json&clean=1")
    fun getNationalData(): Call<List<CovidData>>
}