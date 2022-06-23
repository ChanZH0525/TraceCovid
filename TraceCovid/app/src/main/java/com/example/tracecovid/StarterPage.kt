package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StarterPage : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starterpage)
        auth= FirebaseAuth.getInstance()
        val startBtn: Button = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }

        val loginBtn: Button = findViewById(R.id.loginButton)
        loginBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
    override fun onStart() {
        super.onStart()
        var currentUser= auth.currentUser
        if (currentUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            Toast.makeText(this, "Welcome back, ", Toast.LENGTH_SHORT).show()
            finish()

        }

    }
}