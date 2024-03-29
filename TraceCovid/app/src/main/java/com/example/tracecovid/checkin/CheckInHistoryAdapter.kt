package com.example.tracecovid.checkin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.CheckInHistory

class CheckInHistoryAdapter (var locations: ArrayList<CheckInHistory>):
    RecyclerView.Adapter<CheckInHistoryAdapter.CheckinViewHolder>()
{
    class CheckinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val textLocationName = itemView.findViewById<TextView>(R.id.tv_location_name)
        private val textCheckInDateTime = itemView.findViewById<TextView>(R.id.checkin_date)

        fun bind(location: CheckInHistory) {
            textLocationName.text = location.locationName
            textCheckInDateTime.text = location.checkInDateTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkinhistoryitem,parent,false)
        return CheckinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckinViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

}