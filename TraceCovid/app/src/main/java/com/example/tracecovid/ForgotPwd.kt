package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ForgotPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpwd)

        val backBtn: ImageView = findViewById(R.id.btn_back_forgot_pwd)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val updateBtn: Button = findViewById(R.id.updateBtn)
        updateBtn.setOnClickListener {
            startActivity(Intent(this, PwdUpdatedPage::class.java))
            finish()
        }
    }
}