package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpwd)
        var auth: FirebaseAuth
        auth= FirebaseAuth.getInstance()
        val backBtn: ImageView = findViewById(R.id.btn_back_forgot_pwd)
        backBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        val requestBtn: Button = findViewById(R.id.requestBtn)
        requestBtn.setOnClickListener {
            val email:TextInputEditText= findViewById<TextInputEditText?>(R.id.email)
            val emailadd:String=email.text.toString().trim()
            if (emailadd.isEmpty())
            {
                Toast.makeText(this,"Fill in email address",Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{
                    task ->
                    if(task.isSuccessful)
                    {
                        startActivity(Intent(this, PwdUpdatedPage::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"link was not sent",Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }
}