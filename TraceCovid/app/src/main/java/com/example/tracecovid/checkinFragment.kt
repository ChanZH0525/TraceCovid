package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class checkinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_checkin, container, false)
        val checkinhist_btn:Button= view.findViewById(R.id.checkinhist_btn)
        checkinhist_btn.setOnClickListener{
            requireActivity().run {
                startActivity(Intent(view.context, checkinhistory::class.java))
                finish() // If activity no more needed in back stack
            }
        }
        return view
    }


}