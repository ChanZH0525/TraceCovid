package com.example.tracecovid.home.coviddata

import com.example.tracecovid.data.InternationalCovidData
import retrofit2.Call
import retrofit2.http.GET

interface InternationalCovidService {
    @GET("v3/covid-19/all")
    fun getInternationalData(): Call<InternationalCovidData>
}