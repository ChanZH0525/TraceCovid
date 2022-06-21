package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HotspotFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference
    private  lateinit var firebaseDB: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB= FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference=firebaseDB.reference!!.child("Hotspot")
        val view = inflater.inflate(R.layout.fragment_hotspot, container, false)
        val search: SearchView= view.findViewById(R.id.idSearchView)
        val reportedcases:TextView=view.findViewById(R.id.reportedcases_txt)
        val searchState=search.query.toString()



        return view

    }
}

