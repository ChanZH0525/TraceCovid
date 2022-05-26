package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class HotspotFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hotspot, container, false)
        val searchbtn: ImageView = view.findViewById(R.id.search_loc)

        searchbtn.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, MapsActivity::class.java))
                finish()
            }

        }
        return view

    }
}