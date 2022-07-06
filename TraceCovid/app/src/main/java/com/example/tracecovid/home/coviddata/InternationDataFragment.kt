package com.example.tracecovid.home.coviddata

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.InternationalCovidData
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InternationDataFragment : Fragment() {

    private val TAG = "InternationalDataFragment"
    private val URL = "https://disease.sh/"
    lateinit var internationalRecyclerView: RecyclerView
    lateinit var internationalData: InternationalCovidData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_internation_data, container, false)

//      International Data RecyclerView
        internationalRecyclerView = view.findViewById(R.id.rv_international_stats)
        internationalRecyclerView.layoutManager = LinearLayoutManager(context)

//      Retrieve World Covid-19 Data from https://disease.sh/v3/covid-19/all using Retrofit
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val internationalCovidService = retrofit.create(InternationalCovidService::class.java)

        internationalCovidService.getInternationalData().enqueue(object : Callback<InternationalCovidData>{
            override fun onResponse(
                call: Call<InternationalCovidData>,
                response: Response<InternationalCovidData>
            ) {
                Log.i(TAG, "onResponse $response")
                val internationalDailyData = response.body()
                if (internationalDailyData  == null) {
                    Log.w(TAG, "Did not receive a valid response body")
                    return
                }
                internationalData = internationalDailyData
                var adapter = context?.let { InternationStatisticsAdapter(it, internationalData) }
                internationalRecyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<InternationalCovidData>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

        })


        return view
    }
}