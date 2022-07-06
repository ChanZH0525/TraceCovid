package com.example.tracecovid.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tracecovid.Login
import com.example.tracecovid.R

class PwdUpdatedPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwdupdatedpage)

        val startBtn: Button = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}