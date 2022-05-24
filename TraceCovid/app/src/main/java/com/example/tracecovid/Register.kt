package com.example.tracecovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
        dropdown_country.setAdapter(arrayAdapter_state)
    }

}