package com.example.tracecovid

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class InfoPageOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infopageone)

        val backBtn: ImageView = findViewById(R.id.btn_back_info_page_1)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val toKnowBtn: Button = findViewById(R.id.toKnowBtn)
        toKnowBtn.setOnClickListener {
            startActivity(Intent(this, InfoPageTwo::class.java))
            finish()
        }

        val linkBtn: ImageButton = findViewById(R.id.img)
        linkBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://themalaysianreserve.com/2021/02/17/highly-anticipated-immunisation-to-start-on-time/")
            startActivity(intent)
        }
    }
}