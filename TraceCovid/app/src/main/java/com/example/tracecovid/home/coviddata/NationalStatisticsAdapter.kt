package com.example.tracecovid.home.coviddata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.ProcessedNationalData
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NationalStatisticsAdapter (private var context: Context, private var nationalData: List<ProcessedNationalData>):
RecyclerView.Adapter<NationalStatisticsAdapter.ViewHolder>() {
    private var metrics = arrayOf("Active Cases", "New Cases", "New Recovered", "Critical Cases", "Total Death", "Total Confirmed Cases", "Total Recovered Cases")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.national_stats_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewMetric.text = metrics[position]
        if (nationalData.isNotEmpty()){
            holder.viewValue.text = NumberFormat.getInstance().format(
                when (position) {
                    0 -> { nationalData.last().activeCases }
                    1 -> { nationalData.last().newCases }
                    2 -> { nationalData.last().newRecovered }
                    3 -> { nationalData.last().criticalCases }
                    4 -> { nationalData.last().totalDeath }
                    5 -> { nationalData.last().totalConfirmed }
                    6 -> { nationalData.last().totalRecovered }
                    else -> { "0" }
                }
            )

            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val zone: ZoneId = ZoneId.of("Asia/Kuala_Lumpur")
            val outputDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
            val localDate = LocalDateTime.parse(nationalData.last().lastUpdatedAtApify, inputFormatter)
            val date = localDate.atZone(ZoneId.of("UTC"))
            val zonedTime = date.withZoneSameInstant(zone)
            holder.viewUpdateTime.text = "Until " + outputDateFormat.format(zonedTime).toString()



//******        init line chart
            //        hide grid lines
            holder.viewChart.axisLeft.setDrawGridLines(false)
            val xAxis: XAxis =  holder.viewChart.xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.textSize = 8f

            //remove right y-axis
            holder.viewChart.axisRight.isEnabled = false

            //remove legend
            holder.viewChart.legend.isEnabled = false


            //remove description label
            holder.viewChart.description.isEnabled = false


            //add animation
            holder.viewChart.animateX(1000, Easing.EaseInSine)

            // to draw label on xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f

// ******       set data to chart
            val entries: ArrayList<Entry> = ArrayList()

            //you can replace this data object with  your custom object
            for (i in nationalData.indices) {
                val data = nationalData[i]
                val value = when (position){
                    0 -> { data.activeCases.toFloat() }
                    1 -> { data.newCases.toFloat() }
                    2 -> { data.newRecovered.toFloat() }
                    3 -> { data.criticalCases.toFloat() }
                    4 -> { data.totalDeath.toFloat() }
                    5 -> { data.totalConfirmed.toFloat() }
                    6 -> { data.totalRecovered.toFloat() }
                    else -> {}
                }
                entries.add(Entry(i.toFloat(), value as Float))
            }

            var lineDataSet = LineDataSet(entries, "")
            lineDataSet.lineWidth = 2f
            lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.main_green))
            lineDataSet.color = ContextCompat.getColor(context, R.color.main_green)
            lineDataSet.fillColor = ContextCompat.getColor(context, R.color.main_green)
            lineDataSet.valueTextColor = ContextCompat.getColor(context, R.color.dark_green)
            lineDataSet.valueTextSize = 10f

            val data = LineData(lineDataSet)
            holder.viewChart.data = data

            holder.viewChart.invalidate()

        }

    }

    override fun getItemCount(): Int {
        return metrics.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var viewMetric: TextView = itemView.findViewById(R.id.tv_metrics)
        var viewValue: TextView = itemView.findViewById(R.id.tv_active_cases)
        var viewUpdateTime: TextView = itemView.findViewById(R.id.tv_update_time)
        var viewChart: LineChart = itemView.findViewById(R.id.line_chart_active_cases)

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outputDateFormatChart = DateTimeFormatter.ofPattern("dd-MM", Locale.ENGLISH)
            val localDate = LocalDateTime.parse(nationalData[index].lastUpdatedAtApify, inputFormatter)
            val date = localDate.atZone(ZoneId.of("UTC"))
            val zonedTime = date.withZoneSameInstant(ZoneId.of("Asia/Kuala_Lumpur"))
            return if (index < nationalData.size) {
                outputDateFormatChart.format(zonedTime).toString()
            } else {
                ""
            }
        }
    }
}