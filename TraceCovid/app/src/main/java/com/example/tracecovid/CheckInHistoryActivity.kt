package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckInHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkinhistory)
        val backbtn: ImageView = findViewById(R.id.backbtn)

        backbtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        var locations: ArrayList<CheckInHistory> = arrayListOf(
            CheckInHistory(
                "XMUM Malaysia campus",
                "09/05/2022",
                "12:00:00"
            ),
            CheckInHistory(
                "McDonalds",
                "10/05/2022",
                "14:14:20")
        )


            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recylerviewcheckinhist)

            recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerView.adapter = CheckInHistoryAdapter(locations)
        }


}