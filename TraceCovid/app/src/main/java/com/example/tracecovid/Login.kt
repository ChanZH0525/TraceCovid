package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_btn: Button = findViewById<Button>(R.id.loginBtn)

        login_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val forgotPwd: Button = findViewById(R.id.forgotPwd)
        forgotPwd.setOnClickListener {
            startActivity(Intent(this, ForgotPwd::class.java))
            finish()
        }

        val signUpBtn: Button = findViewById(R.id.signupBtn)
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }
    }
}