package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class HomePageFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)
        val riskass_btn: Button= view.findViewById(R.id.riskass_btn)
        val selfreport_btn: Button= view.findViewById(R.id.selfreport_btn)

        riskass_btn.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, RiskAssessment::class.java))
                finish()
            }
        }
        selfreport_btn.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, SelfReport::class.java))
                finish()
            }
        }


        return view
    }
}


