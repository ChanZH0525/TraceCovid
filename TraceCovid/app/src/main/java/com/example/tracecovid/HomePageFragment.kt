package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout


class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

//      Home Fab handle
        val btnRiskAssessment: LinearLayout = view.findViewById(R.id.btn_risk_asess)
        val btnFAQ: LinearLayout = view.findViewById(R.id.btn_faq)
        val btnInfo: LinearLayout = view.findViewById(R.id.btn_info)
        val btnSelfReport: LinearLayout = view.findViewById(R.id.btn_self_report)

        btnRiskAssessment.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, RiskAssessment::class.java))
                finish()
            }
        }

        btnFAQ.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(view.context, FAQ::class.java))
            }
        }

        btnInfo.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(view.context, InfoPageOne::class.java))
            }
        }

        btnSelfReport.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, SelfReport::class.java))
                finish()
            }
        }


        return view
    }
}


