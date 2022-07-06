package com.example.tracecovid

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracecovid.home.HomePageFragment
import com.example.tracecovid.home.info.InfoPage
import com.example.tracecovid.home.info.InfoPageOne
import com.example.tracecovid.home.info.InfoPageTwo
import com.example.tracecovid.home.coviddata.InternationDataFragment
import com.example.tracecovid.home.coviddata.NationalDataFragment

class ViewPagerAdapter(fragmentActivity: HomePageFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { NationalDataFragment() }
            1 -> { InternationDataFragment() }
            else -> { throw Resources.NotFoundException("Position Not Found") }
        }
    }
}

class ViewPagerAdapter2(fragmentActivity: InfoPage): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> { InfoPageOne() }
            0 -> { InfoPageTwo() }
            else -> { throw Resources.NotFoundException("Position Not Found") }
        }
    }
}