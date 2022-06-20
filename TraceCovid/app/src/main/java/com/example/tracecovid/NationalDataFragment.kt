package com.example.tracecovid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.adapter.NationalStatisticsAdapter


class NationalDataFragment : Fragment() {

    private lateinit var nationalRecyclerView: RecyclerView

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
        nationalRecyclerView.adapter = NationalStatisticsAdapter()

        return view
    }
}