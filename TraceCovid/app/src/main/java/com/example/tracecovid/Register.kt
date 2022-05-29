package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val backBtn: ImageView = findViewById(R.id.btn_back_register)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val signUpBtn: Button = findViewById(R.id.signupBtn)
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    override fun onResume(){
        super.onResume()

        val dropdown_country = findViewById<AutoCompleteTextView>(R.id.dropdown_country)
        val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter_country = ArrayAdapter(this, R.layout.dropdown_list, countries)
        dropdown_country.setAdapter(arrayAdapter_country)

        val dropdown_state = findViewById<AutoCompleteTextView>(R.id.dropdown_state)
        val states = resources.getStringArray(R.array.states)
        val arrayAdapter_state = ArrayAdapter(this, R.layout.dropdown_list, states)
        dropdown_state.setAdapter(arrayAdapter_state)
    }

}