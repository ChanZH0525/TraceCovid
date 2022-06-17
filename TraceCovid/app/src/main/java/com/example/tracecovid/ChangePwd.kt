package com.example.tracecovid

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
        database =FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = database.getReference("Users").child(uid)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_change_pwd, container, false)
        var curpwd:TextInputEditText= view.findViewById(R.id.curpwd)
        var newpwd:TextInputEditText=view.findViewById(R.id.newpwd)
        var newpwd2:TextInputEditText=view.findViewById(R.id.newpwd2)
        val btnUpdate: Button=view.findViewById(R.id.updateBtn)
     /*   btnUpdate.setOnClickListener{
            if (newpwd != newpwd2 && newpwd!= curpwd)
            {
                var password= newpwd.text.toString()
                val map= mapOf(
                    "password" to password
                )
                dbreference.updateChildren(map)
                parentFragmentManager.popBackStack()
            }
            else
            {
                Toast.makeText(activity,"Change of password failed",Toast.LENGTH_SHORT).show()
            }

        } */ //not done yet

        val btnBack: ImageView = view.findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}