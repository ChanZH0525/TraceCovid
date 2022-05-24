package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelfReport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selfreport)

        val back_btn: ImageView = findViewById(R.id.backbtn)
        back_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        var questionnaire2: ArrayList<SelfReportClass> = arrayListOf(
            SelfReportClass("Where was the Swab test done?"),
            SelfReportClass("What date was the test taken?"),
            SelfReportClass("State the outcome of your test."),
            SelfReportClass("Where are you currently staying?"),
            SelfReportClass("Please state your state."),
            SelfReportClass("Please state your current postcode."),
            )
        val recyclerview : RecyclerView = findViewById<RecyclerView>(R.id.selfreport_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerview.adapter = SelfReportAdapter(questionnaire2)
    }

}