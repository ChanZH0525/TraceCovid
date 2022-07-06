package com.example.tracecovid.home.riskassessment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.tracecovid.MainActivity
import com.example.tracecovid.R
import com.example.tracecovid.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RiskAssessmentActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var firebaseDB:FirebaseDatabase
    private lateinit var dbreference:DatabaseReference
    private lateinit var uid: String
    private lateinit var user: ProfileData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_assessment)

        //Initialise all elements in answerArr to 0
        //(0 = no selected radioButton in that radioGroup)
        val answerArr = IntArray(6)
        for (i in answerArr.indices) {
            answerArr[i] = 0
        }

        val rg1:RadioGroup = findViewById(R.id.rg1)
        val rg2:RadioGroup = findViewById(R.id.rg2)
        val rg3:RadioGroup = findViewById(R.id.rg3)
        val rg4:RadioGroup = findViewById(R.id.rg4)
        val rg5:RadioGroup = findViewById(R.id.rg5)
        val rg6:RadioGroup = findViewById(R.id.rg6)
        val radio_yes1:RadioButton = findViewById(R.id.radio_yes1)
        val radio_yes2:RadioButton = findViewById(R.id.radio_yes2)
        val radio_yes3:RadioButton = findViewById(R.id.radio_yes3)
        val radio_yes4:RadioButton = findViewById(R.id.radio_yes4)
        val radio_yes5:RadioButton = findViewById(R.id.radio_yes5)
        val radio_yes6:RadioButton = findViewById(R.id.radio_yes6)
        val radio_no1:RadioButton = findViewById(R.id.radio_no1)
        val radio_no2:RadioButton = findViewById(R.id.radio_no2)
        val radio_no3:RadioButton = findViewById(R.id.radio_no3)
        val radio_no4:RadioButton = findViewById(R.id.radio_no4)
        val radio_no5:RadioButton = findViewById(R.id.radio_no5)
        val radio_no6:RadioButton = findViewById(R.id.radio_no6)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        firebaseDB = FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = firebaseDB.getReference("Users")

        val backBtn: ImageView = findViewById(R.id.btn_back_risk_assessment)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val cancelBtn: Button = findViewById(R.id.cancel_btn)
        cancelBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val submitBtn: Button = findViewById(R.id.submit_btn)
        submitBtn.setOnClickListener {

            val selectedId1:Int = rg1.getCheckedRadioButtonId()
            val selectedId2:Int = rg2.getCheckedRadioButtonId()
            val selectedId3:Int = rg3.getCheckedRadioButtonId()
            val selectedId4:Int = rg4.getCheckedRadioButtonId()
            val selectedId5:Int = rg5.getCheckedRadioButtonId()
            val selectedId6:Int = rg6.getCheckedRadioButtonId()

            //check each radioGroup selectedId
            //if select "yes" radioButton, = 1
            //else if select "no" radioButton, = 2
            if (selectedId1 == radio_yes1.getId()) {
                answerArr[0] = 1
            }
            if (selectedId2 == radio_yes2.getId()) {
                answerArr[1] = 1
            }
            if (selectedId3 == radio_yes3.getId()) {
                answerArr[2] = 1
            }
            if (selectedId4 == radio_yes4.getId()) {
                answerArr[3] = 1
            }
            if (selectedId5 == radio_yes5.getId()) {
                answerArr[4] = 1
            }
            if (selectedId6 == radio_yes6.getId()) {
                answerArr[5] = 1
            }
            if (selectedId1 == radio_no1.getId()) {
                answerArr[0] = 2
            }
            if (selectedId2 == radio_no2.getId()) {
                answerArr[1] = 2
            }
            if (selectedId3 == radio_no3.getId()) {
                answerArr[2] = 2
            }
            if (selectedId4 == radio_no4.getId()) {
                answerArr[3] = 2
            }
            if (selectedId5 == radio_no5.getId()) {
                answerArr[4] = 2
            }
            if (selectedId6 == radio_no6.getId()) {
                answerArr[5] = 2
            }

            checkAnswer(answerArr, this.uid)
        }

        }

    private fun checkAnswer(answerArr: IntArray, uid: String) {
        var noOfYes:Int = 0
        var noOfNo:Int = 0
        for (i in 0..5) {
            if (answerArr[i] == 0) {
                Toast.makeText(this@RiskAssessmentActivity, "Please Answer All Questions!", Toast.LENGTH_SHORT).show()
            } else if (answerArr[i] == 1){
                noOfYes++
            } else{
                noOfNo++
            }
        }

        if(noOfYes + noOfNo == 6 ){

            //Q1 & Q2, any of them ="yes", will show "With Symptom"
            if(answerArr[0] == 1 || answerArr[1] == 1){
                submitRiskResult(1, uid)
            }else{
                submitRiskResult(2, uid)
            }

            //Both Q3 & Q4 ="yes" or Q5 ="yes", will show "High Risk"; any of them ="yes", will show "Medium"
            if ( (answerArr[2] == 1 && answerArr[3] == 1) || answerArr[4] == 1 ){
                submitRiskResult(3, uid)
            }else if (answerArr[2] == 1 || answerArr[3] == 1){
                submitRiskResult(4, uid)
            }else {
                submitRiskResult(5, uid)
            }

            //If all questions ="yes", will show "No Risk", "No Symptom"
            if (noOfYes == 6) {
                submitRiskResult(6, uid)
            }
        }
    }

    private fun submitRiskResult(result: Int, uid: String) {

        if( uid.isNotEmpty())
        {
            dbreference.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user=snapshot.getValue(ProfileData::class.java)!!

                    when (result){
                        1 -> dbreference.child(uid).child("symptom").setValue("With Symptom");

                        2 -> dbreference.child(uid).child("symptom").setValue("No Symptom");

                        3 -> dbreference.child(uid).child("risk").setValue("High Risk");

                        4 -> dbreference.child(uid).child("risk").setValue("Medium Risk");

                        5 -> dbreference.child(uid).child("risk").setValue("Low Risk");

                        6 -> {dbreference.child(uid).child("risk").setValue("Low Risk")
                              dbreference.child(uid).child("symptom").setValue("No Symptom")};

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RiskAssessmentActivity, "User Data Cannot Be Load!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        Toast.makeText(this, "Submit Successfully", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }

    }



