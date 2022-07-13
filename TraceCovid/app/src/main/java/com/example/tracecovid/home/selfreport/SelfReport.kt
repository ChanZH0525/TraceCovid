package com.example.tracecovid.home.selfreport

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tracecovid.MainActivity
import com.example.tracecovid.R
import com.example.tracecovid.data.ProfileData
import com.example.tracecovid.databinding.ActivitySelfreportBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class SelfReport : AppCompatActivity() {

    private lateinit var binding: ActivitySelfreportBinding
    private lateinit var layout: ConstraintLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: ProfileData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelfreportBinding.inflate(layoutInflater)
        layout = binding.root
        setContentView(layout)



        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        firebaseDB = FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = firebaseDB.getReference("Users")

        val backBtn: ImageView = findViewById(R.id.btn_back_self_report)
        backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val cancelBtn: Button = findViewById(R.id.cancel_btn)
        cancelBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val answerSR1 = findViewById<AutoCompleteTextView>(R.id.dropdown_swabLocation)
        val answerSR3 = findViewById<AutoCompleteTextView>(R.id.dropdown_swabOutcome)
        val answerSR4 = findViewById<AutoCompleteTextView>(R.id.dropdown_states)

        val date = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.self_report_ans2)
        //Put "validate" to detect whether user has clicked to Q2 edittext, 0 = never ; 1 = yes
        var validate:Int = 0
        date.setOnClickListener {
            validate = 1
            DatePickerUniversal(date, "dd-MMM-yyyy")
        }

        val submitBtn: Button = findViewById(R.id.submit_btn)
        submitBtn.setOnClickListener {

            val swabLocation:String = answerSR1.text.toString()
            val swabDate:String = date.text.toString()
            val swabOutcome:String = answerSR3.text.toString()
            val swabState:String = answerSR4.text.toString()

            //Get the Date of Today
            val c:Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault())
            val dateToday:String = sdf.format(c.time)

            if(validate == 1){

                val dateOfToday:Date = SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH).parse(dateToday)
                val selectedDate:Date = SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH).parse(swabDate)

                //To check if any of the Q1 & Q3 & Q4 has no selected answer
                if (swabLocation == "" || swabState == "" || swabOutcome == ""){
                    Snackbar.make(layout, "Please Answer All Questions!", Snackbar.LENGTH_SHORT).show()
                }
                else{
                    //To check if date selected by user is on or before date of today
                    checkDate(dateOfToday, selectedDate, this.uid, swabLocation, swabDate, swabOutcome,swabState)
                }

            }else{
                Snackbar.make(layout, "Please Select A Date!", Snackbar.LENGTH_SHORT).show()
            }

        }


    }

    private fun delayFunction(function: ()-> Unit, delay:Long){
        Handler().postDelayed(function,delay)
    }

    private fun checkDate(dateOfToday:Date, selectedDate:Date, uid: String, swabLocation:String, swabDate:String, swabOutcome:String, swabState:String,) {
        if(dateOfToday.compareTo(selectedDate) <= 0 ){
            Snackbar.make(layout, "Please Select A Valid Date!", Snackbar.LENGTH_SHORT).show()
        }else{
            submitSRResult(swabLocation, swabDate, swabOutcome,swabState, this.uid)
        }
    }

    override fun onResume(){
        super.onResume()

        val dropdownSwabLocation = findViewById<AutoCompleteTextView>(R.id.dropdown_swabLocation)
        val swabLocation = resources.getStringArray(R.array.swabLocation)
        val arrayAdapterSwabLocation = ArrayAdapter(this, R.layout.dropdown_list, swabLocation)
        dropdownSwabLocation.setAdapter(arrayAdapterSwabLocation)

        val dropdownSwabOutcome = findViewById<AutoCompleteTextView>(R.id.dropdown_swabOutcome)
        val swabOutcome = resources.getStringArray(R.array.swabOutcome)
        val arrayAdapterSwapOutcome = ArrayAdapter(this, R.layout.dropdown_list, swabOutcome)
        dropdownSwabOutcome.setAdapter(arrayAdapterSwapOutcome)

        val dropdownStates = findViewById<AutoCompleteTextView>(R.id.dropdown_states)
        val state = resources.getStringArray(R.array.states)
        val arrayAdapterStates = ArrayAdapter(this, R.layout.dropdown_list, state)
        dropdownStates.setAdapter(arrayAdapterStates)
    }

    class DatePickerUniversal(private val mEditText: EditText, format: String?) :
        View.OnFocusChangeListener, OnDateSetListener, View.OnClickListener {
        private var mCalendar: Calendar? = null
        private val mFormat: SimpleDateFormat
        override fun onFocusChange(view: View, hasFocus: Boolean) {
            if (hasFocus) {
                showPicker(view)
            }else{
                mEditText.setText("")
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
            DatePickerDialog(view.context, this, year, month, day).show()
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {

            mCalendar!!.set(Calendar.YEAR, year)
            mCalendar!!.set(Calendar.MONTH, month)
            mCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            mEditText.setText(mFormat.format(mCalendar!!.time))

        }

        init {
            mEditText.onFocusChangeListener = this
            mEditText.setOnClickListener(this)
            mFormat = SimpleDateFormat(format, Locale.getDefault())
        }
    }

    private fun submitSRResult(swabLocation:String, swabDate:String, swabOutcome:String, swabState:String, uid:String) {

        if( uid.isNotEmpty())
        {
            if(swabOutcome == "Positive"){
                dbreference.child(uid).child("risk").setValue("High Risk");
                dbreference.child(uid).child("symptom").setValue("(Positive)");
            }
//            else{
//                dbreference.child(uid).child("risk").setValue("High Risk");
//                dbreference.child(uid).child("symptom").setValue("No Symptom");
//            }
            dbreference.child(uid).child("state").setValue(swabState);
        }


        Toast.makeText(this, "Submit Successfully", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

}
}


