package com.example.tracecovid

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.tracecovid.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var actionBar:ActionBar
    private lateinit var progressDialog:ProgressDialog
    private lateinit var auth:FirebaseAuth
    private var phonenumber:String=""
    private var password:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  actionBar=supportActionBar!!
      //  actionBar.title="Login"
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in")
        progressDialog.setCanceledOnTouchOutside(false)

        auth= FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener{
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener{
            validatedata()

        }
        binding.forgotPwd.setOnClickListener{
            startActivity(Intent(this, ForgotPwd::class.java))
            finish()
        }
        checkuser()
    }

    private fun validatedata() {
        phonenumber=binding.phoneNumber.toString().trim()
        password=binding.pwd.toString().trim()

     //   if(!Patterns.EMAIL_ADDRESS.matcher(phonenumber).matches()){
       //     binding.phoneNumber.setError  ("invalid phone number format")
        //}
        if(TextUtils.isEmpty(password)){
            binding.pwd.setError("Plase enter password")
        }
        else
        {
            progressDialog.show()
            auth.signInWithEmailAndPassword(phonenumber,password).
            addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser=auth.currentUser
                val phone=firebaseUser!!.phoneNumber
                Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }.addOnFailureListener{
               progressDialog.dismiss()
               Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkuser() {
        val firebaseUser=auth.currentUser
        if(firebaseUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}