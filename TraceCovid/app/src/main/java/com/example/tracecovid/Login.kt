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
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()

        binding.forgotPwd.setOnClickListener{
            startActivity(Intent(this, ForgotPwd::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener{
            val email=binding.loginEmail.text.toString()
            val pwd=binding.loginPwd.text.toString()
            if(email.isNotEmpty()&&pwd.isNotEmpty())
            {
                    firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else
                {
                    Toast.makeText(this,"Fill in all details",Toast.LENGTH_SHORT).show()
                }

        }

    }




}