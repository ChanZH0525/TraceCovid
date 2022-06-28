package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tracecovid.databinding.ActivityPhoneNumberBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var layout: View
    private val TAG = PhoneNumberActivity::class.java.simpleName
    lateinit var codeBySystem: String
    private lateinit var auth: FirebaseAuth
    private lateinit var userPhone: String
    private  lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private val constantFictionalNumber = "+15555215554"
    lateinit var phoneNumber: EditText
    lateinit var nextIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        layout = binding.root
        setContentView(layout)
        firebaseDB = FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        phoneNumber = findViewById(R.id.phoneNumber)

        dbreference = firebaseDB.getReference()
        auth = FirebaseAuth.getInstance()

        var otpNumber: EditText = findViewById(R.id.otpnumber)
        var otpRequest: TextView = findViewById(R.id.requestotp)
        var nextBtn: Button = findViewById(R.id.nextbtn)


        nextBtn.setOnClickListener{
            val code = otpNumber.text.toString()
            verifyCode(code)

        }
        binding.btnBackRegister.setOnClickListener{
            startActivity(Intent(this, StarterPage::class.java))
            finish()
        }

        val callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                codeBySystem = p0
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$p0")
                val code = p0.smsCode
                if(code != null) otpNumber.setText(code)
                
                verifyCode(code)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Snackbar.make(layout, "Verification Failed", Snackbar.LENGTH_LONG).show()
            }

        }
        otpRequest.setOnClickListener {
            userPhone = "+" + "601" + phoneNumber.text
            Log.d(TAG, userPhone)
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(userPhone)
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callBacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun verifyCode(code: String?) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(codeBySystem,
            code.toString()
        )
        verifyWithCredential(credential)

    }

    private fun verifyWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Snackbar.make(layout, "Phone Verification Successful", Snackbar.LENGTH_LONG).show()
                    nextIntent = Intent(this, Register::class.java)
                    nextIntent.putExtra("PhoneNumber",userPhone)
                    startActivity(nextIntent)
                    finish()


                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Snackbar.make(layout, "Phone Verification Failed", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

    }
}