package com.example.tracecovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tracecovid.R
import com.example.tracecovid.databinding.ActivityCheckInBinding
import com.example.tracecovid.databinding.ActivityPhoneNumberBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var layout: View
    lateinit var codeBySystem: String
    private lateinit var auth: FirebaseAuth
    private val constantFictionalNumber = "+15 555215554"
    lateinit var nextIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        layout = binding.root
        setContentView(layout)
        auth = FirebaseAuth.getInstance()
        var phoneNumber: TextInputLayout = findViewById(R.id.phoneNumber)
        var otpNumber: TextInputLayout = findViewById(R.id.otpnumber)
        var otpRequest: TextView = findViewById(R.id.requestotp)
        var nextBtn:Button=findViewById(R.id.nextbtn)


        nextBtn.setOnClickListener{
            val code = otpNumber.editText.toString()
            verifyCode(code)
        }

        val callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                codeBySystem = p0
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code = p0.smsCode
                if(code != null) otpNumber.editText?.setText(code)
                
                verifyCode(code)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext, "Verification failed", Toast.LENGTH_SHORT).show()
            }

        }
        otpRequest.setOnClickListener {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(constantFictionalNumber)
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
                    nextIntent.putExtra("PhoneNumber",constantFictionalNumber)
                    startActivity(nextIntent)
                    finish()

                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Snackbar.make(layout, "Phone Verification Failed", Snackbar.LENGTH_LONG).show()
                    }
                    // Update UI
                }
            }

    }
}