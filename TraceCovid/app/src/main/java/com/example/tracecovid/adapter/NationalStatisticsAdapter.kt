package com.example.tracecovid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.github.mikephil.charting.charts.LineChart

class NationalStatisticsAdapter: RecyclerView.Adapter<NationalStatisticsAdapter.ViewHolder>() {
    private var metrics = arrayOf("Active Cases", "New Cases", "New Recovered", "Total Death", "Total Confirmed Cases", "Total Recovered Cases")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NationalStatisticsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.national_stats_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NationalStatisticsAdapter.ViewHolder, position: Int) {
        holder.viewMetric.text = metrics[position]
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
}