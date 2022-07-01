package com.example.tracecovid

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tracecovid.data.CovidData
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList


class HomePageFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var activeCases: TextView
    private lateinit var timeActiveCases: TextView
    private lateinit var lineChart: LineChart
    private var dataList = ArrayList<CovidData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

//      Home Fab handle
        val btnRiskAssessment: LinearLayout = view.findViewById(R.id.btn_risk_asess)
        val btnFAQ: LinearLayout = view.findViewById(R.id.btn_faq)
        val btnInfo: LinearLayout = view.findViewById(R.id.btn_info)
        val btnSelfReport: LinearLayout = view.findViewById(R.id.btn_self_report)

//        TabLayout and ViewPager2 handle
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager_statistics)

        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){ tab,index ->
            tab.text = when(index){
                0 -> {"National"}
                1 -> {"International"}
                else -> {throw Resources.NotFoundException("Position Not Found")}
            }
        }.attach()


//        //        Statistics
//        val gson = GsonBuilder().create()
//        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//        val covidService = retrofit.create(CovidService::class.java)
//
//        covidService.getNationalData().enqueue(object: Callback<List<CovidData>>{
//            override fun onResponse(call: Call<List<CovidData>>, response: Response<List<CovidData>>) {
//                Log.i(TAG, "onResponse $response")
//                val nationalData = response.body()
//                if(nationalData == null){
//                    Log.w(TAG, "Did not receive a valid response body")
//                    return
//                }
//                var nationalDailyData = nationalData.reversed()
//                Log.i(TAG, "Update graph with national data")
//                displayLatestData(nationalDailyData)
//            }
//
//            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
//                Log.e(TAG, "onFailure $t")
//            }
//        })




        btnRiskAssessment.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, RiskAssessmentActivity::class.java))
                finish()
            }
        }

        btnFAQ.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(view.context, FAQ::class.java))
                finish()
            }
        }

        btnInfo.setOnClickListener{
            requireActivity().run{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, InfoPage())
                    .addToBackStack(null)
                    .commit()
            }
        }

        btnSelfReport.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, SelfReport::class.java))
                finish()
            }
        }

        return view
    }

//    private fun setDataToLineChart() {
//        val entries: ArrayList<Entry> = ArrayList()
//
//        //you can replace this data object with  your custom object
//        for (i in dataList.indices) {
//            val data = dataList[i]
//            entries.add(Entry(i.toFloat(), data.activeCases.toFloat()))
//        }
//
//        var lineDataSet = LineDataSet(entries, "")
//        lineDataSet.lineWidth = 2f
//        lineDataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.main_green))
//        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.main_green)
//        lineDataSet.fillColor = ContextCompat.getColor(requireContext(), R.color.main_green)
//        lineDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.dark_green)
//        lineDataSet.valueTextSize = 10f
//
//        val data = LineData(lineDataSet)
//        lineChart.data = data
//
//        lineChart.invalidate()
//    }
//
//    private fun initLineChart() {
//        //        hide grid lines
//        lineChart.axisLeft.setDrawGridLines(false)
//        val xAxis: XAxis = lineChart.xAxis
//        xAxis.setDrawGridLines(false)
//        xAxis.setDrawAxisLine(false)
//        xAxis.textSize = 8f
//
//        //remove right y-axis
//        lineChart.axisRight.isEnabled = false
//
//        //remove legend
//        lineChart.legend.isEnabled = false
//
//
//        //remove description label
//        lineChart.description.isEnabled = false
//
//
//        //add animation
//        lineChart.animateX(1000, Easing.EaseInSine)
//
//        // to draw label on xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
//        xAxis.valueFormatter = MyAxisFormatter()
//        xAxis.setDrawLabels(true)
//        xAxis.granularity = 1f
//    }
//
//    inner class MyAxisFormatter : IndexAxisValueFormatter() {
//
//        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//            val index = value.toInt()
//            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
//            val outputDateFormatChart = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
//            val date = LocalDateTime.parse(dataList[index].lastUpdatedAtApify, inputFormatter)
//            return if (index < dataList.size) {
//                outputDateFormatChart.format(date).toString()
//            } else {
//                ""
//            }
//        }
//    }
//
//    private fun displayLatestData(dailyData: List<CovidData>) {
//        activeCases.text = NumberFormat.getInstance().format(dailyData.first().activeCases)
//
//
//        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
//        val outputDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
//        val date = LocalDateTime.parse(dailyData.first().lastUpdatedAtApify, inputFormatter)
//        timeActiveCases.text = "Until " + outputDateFormat.format(date).toString()
//
//        val outputDateFormatChart = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
//
//
////        get data of recent 7 days
//        for(i in 6 downTo 0)
//        {
//            dataList.add(dailyData[i])
//        }
//        initLineChart()
//        setDataToLineChart()
//
//    }


}


