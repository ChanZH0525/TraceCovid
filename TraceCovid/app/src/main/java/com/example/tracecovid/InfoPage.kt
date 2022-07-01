package com.example.tracecovid

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tracecovid.data.CovidData
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class InfoPage : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.infopage, container, false)

        val backBtn: ImageView = view.findViewById(R.id.btn_back_info_page_1)
        backBtn.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, MainActivity::class.java))
                finish()
            }
        }


//        TabLayout and ViewPager2 handle
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout_infopage)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager_infopage)

        viewPager.adapter = ViewPagerAdapter2(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                1 -> {
                    "Things To Do"
                }
                0 -> {
                    "Things To Know"
                }
                else -> {
                    throw Resources.NotFoundException("Position Not Found (InfoPage)")
                }
            }
        }.attach()

        return view
    }
}