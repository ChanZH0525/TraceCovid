package com.example.tracecovid.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tracecovid.BaseFragment
import com.example.tracecovid.R


class UpdatePhoneNo : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_phone_no, container, false)
        val btnBack: ImageView = view.findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }
}