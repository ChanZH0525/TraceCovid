package com.example.tracecovid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.InternationalCovidData

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

//        holder.viewUpdateTime.text =
    }

    override fun getItemCount(): Int {
        return metrics.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var viewMetric: TextView = itemView.findViewById(R.id.tv_metrics)
        var viewValue: TextView = itemView.findViewById(R.id.tv_value)
        var viewUpdateTime: TextView = itemView.findViewById(R.id.tv_update_time)
        var viewValueToday: TextView = itemView.findViewById(R.id.tv_today_value)
    }
}