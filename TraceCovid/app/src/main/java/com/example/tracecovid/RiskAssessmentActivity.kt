package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RiskAssessmentActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var firebaseDB:FirebaseDatabase
    private lateinit var dbreference:DatabaseReference
    private lateinit var uid: String
    private lateinit var user:ProfileData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_assessment)

        val answerArr = IntArray(4)
        for (i in 0..3) {
            answerArr[i] = 0
        }

        var rg1:RadioGroup = findViewById(R.id.rg1)
        var rg2:RadioGroup = findViewById(R.id.rg2)
        var rg3:RadioGroup = findViewById(R.id.rg3)
        var rg4:RadioGroup = findViewById(R.id.rg4)
        var radio_yes1:RadioButton = findViewById(R.id.radio_yes1)
        var radio_yes2:RadioButton = findViewById(R.id.radio_yes2)
        var radio_yes3:RadioButton = findViewById(R.id.radio_yes3)
        var radio_yes4:RadioButton = findViewById(R.id.radio_yes4)
        var radio_no1:RadioButton = findViewById(R.id.radio_no1)
        var radio_no2:RadioButton = findViewById(R.id.radio_no2)
        var radio_no3:RadioButton = findViewById(R.id.radio_no3)
        var radio_no4:RadioButton = findViewById(R.id.radio_no4)

        auth = FirebaseAuth.getInstance()
        uid=auth.currentUser?.uid.toString()
        firebaseDB= FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference=firebaseDB.getReference("Users")

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

            checkAnswer(answerArr, this.uid)
        }

        }

    private fun checkAnswer(answerArr: IntArray, uid: String) {
        var noOfYes:Int = 0
        var noOfNo:Int = 0
        for (i in 0..3) {
            if (answerArr[i] == 0) {
                Toast.makeText(this@RiskAssessmentActivity, "Please Answer All Questions!", Toast.LENGTH_SHORT).show()
            } else if (answerArr[i] == 1){
                noOfYes++
            } else{
                noOfNo++
            }
        }

        if(noOfYes + noOfNo == 4 ){

            if (noOfYes == 4) {
                submitRiskResult(3, uid)
            }else if (noOfYes == 2 || noOfYes == 3){
                submitRiskResult(2, uid)
            }else{
                submitRiskResult(1, uid)
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
                        1 -> dbreference.child(uid).child("status").setValue("Low Risk");

                        2 -> dbreference.child(uid).child("status").setValue("Medium Risk");

                        3 -> dbreference.child(uid).child("status").setValue("High Risk");
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    //Toast.makeText(this, "User Data Cannot Be Load!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        Toast.makeText(this, "Submit Successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }

    }



