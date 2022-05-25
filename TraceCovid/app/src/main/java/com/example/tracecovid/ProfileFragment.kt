package com.example.tracecovid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

//        handle for profile information
        val profileImage: CircleImageView = view.findViewById(R.id.iv_profile)
        val btnSetting: ImageView = view.findViewById(R.id.btn_setting_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvPhone: TextView = view.findViewById(R.id.tv_phone)
        val tvIC: TextView = view.findViewById(R.id.tv_ic_passport)
        val tvState: TextView = view.findViewById(R.id.tv_state)




        return view
    }
}