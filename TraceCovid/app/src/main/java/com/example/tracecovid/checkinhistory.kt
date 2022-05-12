package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class checkinhistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkinhistory)
        val backbtn: ImageView =findViewById(R.id.backbtn)
        backbtn.setOnClickListener{
            val fragment: Fragment = checkinFragment()

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
    }

}