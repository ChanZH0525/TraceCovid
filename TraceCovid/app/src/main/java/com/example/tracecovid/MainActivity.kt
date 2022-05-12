package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,homepageFragment()).commit()

        var bottom_navigation:BottomNavigationView =findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemReselectedListener(navlistener)


    }
    val navlistener =BottomNavigationView.OnNavigationItemReselectedListener {
        when(it.itemId)
        {
            R.id.homepage->fragment=homepageFragment()
            R.id.checkin->fragment=checkinFragment()
            R.id.Hotspot->fragment=hotspotFragment()
            R.id.profile->fragment=profileFragment()

        }
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
        true
    }

}