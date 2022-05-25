package com.example.tracecovid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class CheckInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_checkin, container, false)
//        handle for profile information
        val btnCheckInHistory: ImageView = view.findViewById(R.id.btn_check_in_history)
        val profilePicture: CircleImageView = view.findViewById(R.id.iv_profile_image_checkin)
        val username: TextView = view.findViewById(R.id.tv_username_checkin)
        val ic: TextView = view.findViewById(R.id.tv_ic_checkin)
        val riskStatus: TextView = view.findViewById(R.id.tv_risk_status)

        var locations: ArrayList<CheckInHistory> = arrayListOf(
            CheckInHistory(
                "XMUM Malaysia campus",
                "09/05/2022",
                "12:00:00"
            ),
            CheckInHistory(
                "McDonalds",
                "10/05/2022",
                "14:14:20")
        )

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_recent)

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = CheckInHistoryAdapter(locations)

        btnCheckInHistory.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, AllCheckInHistory())
                .addToBackStack(null)
                .commit()
        }
        return view
    }


}