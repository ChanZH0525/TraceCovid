package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text


class ChangePwd : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    lateinit var database: FirebaseDatabase
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database =
            FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = database.getReference("Users").child(uid)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_change_pwd, container, false)
        var emailadd: TextInputEditText = view.findViewById(R.id.curemail)

        val btnUpdate: Button = view.findViewById(R.id.updateBtn)
        btnUpdate.setOnClickListener {
            val email = emailadd.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(activity, "Fill in email address", Toast.LENGTH_SHORT).show()
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        requireActivity().run {
                            startActivity(Intent(view.context, PwdUpdatedPage::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(activity, "link was not sent", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }//not done yet

            val btnBack: ImageView = view.findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            return view

        }
    }
