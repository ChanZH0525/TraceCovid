package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RiskAssessment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_assessment)
        val back_btn: ImageView = findViewById(R.id.btn_back_check_in)
        back_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        var questionnaire: ArrayList<RiskAssessmentClass> = arrayListOf(
            RiskAssessmentClass("Do you have the following symptoms?\n - Fever \n - Cough \n - Sore Throat \n - Diarrhea \n - Fatigue \n - Body Ache"),
            RiskAssessmentClass("Have you attended any event/area associated with a known Covid-19 cluster?"),
            RiskAssessmentClass("Have you travelled abroad in the last 14 days?"),
            RiskAssessmentClass("Have you had close contact with any suspected/confirmed cases of Covid-19 in the last 14 days?"),

        )
        val recyclerview : RecyclerView= findViewById<RecyclerView>(R.id.riskassess_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerview.adapter = RiskAssessmentAdapter(questionnaire)
    }

}