package com.example.tracecovid

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class SelfReport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selfreport)

        val back_btn: ImageView = findViewById(R.id.btn_back_self_report)
        back_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val cancelBtn: Button = findViewById(R.id.cancel_btn)
        cancelBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        var date = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.self_report_ans2)
        DatePickerUniversal(date, "dd-MMM-yyyy")

    }

    override fun onResume(){
        super.onResume()

        val dropdown_swabLocation = findViewById<AutoCompleteTextView>(R.id.dropdown_swabLocation)
        val swabLocation = resources.getStringArray(R.array.swabLocation)
        val arrayAdapter_swabLocation = ArrayAdapter(this, R.layout.dropdown_list, swabLocation)
        dropdown_swabLocation.setAdapter(arrayAdapter_swabLocation)

        val dropdown_swabOutcome = findViewById<AutoCompleteTextView>(R.id.dropdown_swabOutcome)
        val swabOutcome = resources.getStringArray(R.array.swabOutcome)
        val arrayAdapter_swapOutcome = ArrayAdapter(this, R.layout.dropdown_list, swabOutcome)
        dropdown_swabOutcome.setAdapter(arrayAdapter_swapOutcome)

        val dropdown_states = findViewById<AutoCompleteTextView>(R.id.dropdown_states)
        val state = resources.getStringArray(R.array.states)
        val arrayAdapter_states = ArrayAdapter(this, R.layout.dropdown_list, state)
        dropdown_states.setAdapter(arrayAdapter_states)
    }

    class DatePickerUniversal(private val mEditText: EditText, format: String?) :
        View.OnFocusChangeListener, OnDateSetListener, View.OnClickListener {
        private var mCalendar: Calendar? = null
        private val mFormat: SimpleDateFormat
        override fun onFocusChange(view: View, hasFocus: Boolean) {
            if (hasFocus) {
                showPicker(view)
            }
        }

        override fun onClick(view: View) {
            showPicker(view)
        }

        private fun showPicker(view: View) {
            if (mCalendar == null) mCalendar = Calendar.getInstance()
            val day: Int = mCalendar!!.get(Calendar.DAY_OF_MONTH)
            val month: Int = mCalendar!!.get(Calendar.MONTH)
            val year: Int = mCalendar!!.get(Calendar.YEAR)
            DatePickerDialog(view.getContext(), this, year, month, day).show()
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
            mCalendar!!.set(Calendar.YEAR, year)
            mCalendar!!.set(Calendar.MONTH, month)
            mCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            mEditText!!.setText(mFormat.format(mCalendar!!.getTime()))
        }

        init {
            mEditText.setOnFocusChangeListener(this)
            mEditText.setOnClickListener(this)
            mFormat = SimpleDateFormat(format, Locale.getDefault())
        }
    }

}