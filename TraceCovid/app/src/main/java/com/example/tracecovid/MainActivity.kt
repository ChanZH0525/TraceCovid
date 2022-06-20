package com.example.tracecovid

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewGroup.*
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tracecovid.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

   // private lateinit var fragment: Fragment
    lateinit var navView: BottomNavigationView
    lateinit var binding: ActivityMainBinding
 //   private lateinit var actionBar: ActionBar
  //  private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        navView = findViewById(R.id.bottom_nav_view)
        navView.setupWithNavController(navController)
    }

    fun setBottomNavigationVisibility(visibility: Int){
        navView.visibility = visibility
    }

}


