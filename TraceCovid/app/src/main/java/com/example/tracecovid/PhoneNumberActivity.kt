package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tracecovid.R
import com.google.android.material.textfield.TextInputLayout

class PhoneNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
        var phonenumber: TextInputLayout = findViewById(R.id.phoneNumber)
        var otpnumber: TextInputLayout = findViewById(R.id.otpnumber)
        var otprequest: TextView = findViewById(R.id.requestotp)
        var nextbtn:Button=findViewById(R.id.nextbtn)
        nextbtn.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
            finish()
        }
        otprequest.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}