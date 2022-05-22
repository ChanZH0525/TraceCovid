package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class riskAssessment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_assessment)
        val back_btn: ImageView = findViewById(R.id.backbtn)
        back_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        var questionnaire: ArrayList<RiskAssessmentClass> = arrayListOf(
            RiskAssessmentClass("Testing 1,2,3"),
            RiskAssessmentClass("Testing2 1,2,3"),
            RiskAssessmentClass("Testing 1,2,3"),
            RiskAssessmentClass("Testing 1,2,3"),
            RiskAssessmentClass("Testing 1,2,3"),
            RiskAssessmentClass("Testing 1,2,3"),

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