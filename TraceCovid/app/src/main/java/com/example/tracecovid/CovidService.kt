package com.example.tracecovid

import com.example.tracecovid.data.CovidData
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("items?format=json&clean=1")
    fun getNationalData(): Call<List<CovidData>>
}