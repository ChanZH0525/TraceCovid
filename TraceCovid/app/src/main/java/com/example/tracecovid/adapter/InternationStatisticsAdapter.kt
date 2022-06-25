package com.example.tracecovid.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.InternationalCovidData
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class InternationStatisticsAdapter (private val context: Context, private val internationalData: InternationalCovidData):
    RecyclerView.Adapter<InternationStatisticsAdapter.ViewHolder>(){
    private var metrics = arrayOf("Total Confirmed Cases", "Total Recovered", "Total Death", "Active Cases", "Critical Cases")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InternationStatisticsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.international_stats_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InternationStatisticsAdapter.ViewHolder, position: Int) {
        holder.viewMetric.text = metrics[position]
        val updateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(internationalData.updated), ZoneId.of("Asia/Kuala_Lumpur"))
        val outputDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
        holder.viewUpdateTime.text = outputDateFormat.format(updateTime).toString()
        holder.viewValue.text = NumberFormat.getInstance().format(when(position){
            0 -> { internationalData.cases }
            1 -> { internationalData.recovered }
            2 -> { internationalData.deaths }
            3 -> { internationalData.active }
            4 -> { internationalData.critical }
            else -> { "0" }
        })

        holder.viewValueToday.text = when(position) {
            0 -> {  "+ " + NumberFormat.getInstance().format(internationalData.todayCases).toString() + " (Today)" }
            1 -> {  "+ " + NumberFormat.getInstance().format(internationalData.todayRecovered).toString() + " (Today)" }
            2 -> {  "+ " + NumberFormat.getInstance().format(internationalData.todayDeaths).toString() + " (Today)" }
            else -> { "0" }
        }

        holder.layoutToday.visibility = when(position){
            3,4 -> { View.GONE }
            else -> { View.VISIBLE }
        }

    }

    override fun getItemCount(): Int {
        return metrics.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val viewMetric: TextView = itemView.findViewById(R.id.tv_metrics)
        val viewValue: TextView = itemView.findViewById(R.id.tv_value)
        val viewUpdateTime: TextView = itemView.findViewById(R.id.tv_update_time)
        val viewValueToday: TextView = itemView.findViewById(R.id.tv_today_value)
        val layoutToday: ConstraintLayout = itemView.findViewById(R.id.layout_stats_today)
    }
}