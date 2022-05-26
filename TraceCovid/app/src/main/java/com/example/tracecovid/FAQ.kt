package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FAQ : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        val back_btn: ImageView = findViewById(R.id.btn_back_faq)
        back_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        var faq: ArrayList<FAQClass> = arrayListOf(
            FAQClass(
                "What is TraceCovid App?",
                "TraceCovid is an application to assist in managing the COVID-19 outbreaks in the country. It allows users to perform health self-assessment on themselves. The users can also monitor their health progress throughtout the COVID-19 outbreak."
            ),
            FAQClass(
                "Who can use TraceCovid App?",
                "TraceCovid App is used by Malaysians and residents of Malaysia.",
                ),
            FAQClass(
                "What are the laws related to the implementation of TraceCovid?",
                "TraceCovid was developed to support the implementation of the Prevention and Control of Infectious Disease Act 1988 [Act 342].\n" +
                        "\n" +
                        "Providing false infomation is an offence under Section 22 of the Prevention and Control of Infectious Diseases Act 1988 [Act 342] and Section 233 of the Communication and Muntimedia Act 1988 [Act 588].",
            )
        )


        val recyclerview : RecyclerView= findViewById<RecyclerView>(R.id.faq_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerview.adapter = FAQAdapter(faq)
    }
}