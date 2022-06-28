package com.example.tracecovid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CheckInHistoryAdapter (var locations: ArrayList<CheckInHistory>):
    RecyclerView.Adapter<CheckInHistoryAdapter.CheckinViewHolder>()
{
    class CheckinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tv_location_name = itemView.findViewById<TextView>(R.id.tv_location_name)
        val checkin_time = itemView.findViewById<TextView>(R.id.checkin_time)
        val checkin_date = itemView.findViewById<TextView>(R.id.checkin_date)
        fun bind(location: CheckInHistory) {
            tv_location_name.text = location.locationname
            checkin_time.text=location.checkinTime
            checkin_date.text = location.checkinDate
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