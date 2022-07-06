package com.example.tracecovid.home.coviddata

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.CovidData
import com.example.tracecovid.data.ProcessedNationalData
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class NationalDataFragment : Fragment() {

    private val TAG = "NationalDataFragment"
    private val URL = "https://api.apify.com/v2/datasets/7Fdb90FMDLZir2ROo/"
    private lateinit var nationalRecyclerView: RecyclerView
    private var dataList = ArrayList<ProcessedNationalData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_national_data, container, false)

        //        National statistics Recycler View
        nationalRecyclerView = view.findViewById(R.id.rv_national_stats)
        nationalRecyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = context?.let { NationalStatisticsAdapter(it, dataList) }
        nationalRecyclerView.adapter = adapter

            //        Statistics
            val gson = GsonBuilder().create()
            val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            val covidService = retrofit.create(CovidService::class.java)

            covidService.getNationalData().enqueue(object : Callback<List<CovidData>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<CovidData>>,
                    response: Response<List<CovidData>>
                ) {
                    Log.i(TAG, "onResponse $response")
                    val nationalData = response.body()
                    if (nationalData == null) {
                        Log.w(TAG, "Did not receive a valid response body")
                        return
                    }
                    var nationalDailyData = nationalData.reversed()
                    Log.i(TAG, "Update graph with national data")
                    //        get data of recent 7 days
                    for (i in 6 downTo 0) {
                        var processedData = ProcessedNationalData()
                        processedData.activeCases = nationalDailyData[i].activeCases
//                        Calculate daily new cases by subtracting total testedPositive at (i-1)th day from total testPositive at i day
                        processedData.newCases =
                            nationalDailyData[i].testedPositive - nationalDailyData[i + 1].testedPositive
//                        Calculate daily new recovered cases by subtracting total recovered at (i-1)th day from total recovered at i day
                        processedData.newRecovered =
                            nationalDailyData[i].recovered - nationalDailyData[i + 1].recovered
                        processedData.criticalCases = nationalDailyData[i].critical
                        processedData.totalDeath = nationalDailyData[i].deceased
                        processedData.totalConfirmed = nationalDailyData[i].testedPositive
                        processedData.totalRecovered = nationalDailyData[i].recovered
                        processedData.lastUpdatedAtApify = nationalDailyData[i].lastUpdatedAtApify
//                        Add the ProcessedNationalData object to the datalist
                        dataList.add(processedData)
                    }
                    adapter?.notifyDataSetChanged()

                }

                override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                    Log.e(TAG, "onFailure $t")
                }
            })
            return view
        }

}