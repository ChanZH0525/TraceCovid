package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StarterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starterpage)

        val startBtn: Button = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }

        val loginBtn: Button = findViewById(R.id.loginButton)
        loginBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}