package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


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
        dbreference=firebaseDB.getReference("Hotspot")
        val view = inflater.inflate(R.layout.fragment_hotspot, container, false)
        val search: EditText= view.findViewById(R.id.idSearchView)
        val searchbtn: Button =view.findViewById(R.id.search_btn)
        var reportedcases:TextView=view.findViewById(R.id.reportedcases_txt)
        var cases:HotpotData

        searchbtn.setOnClickListener{
            dbreference.child(search.text.toString()).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    cases= snapshot.getValue(HotpotData::class.java)!!
                    reportedcases.setText("There are "+cases.Cases +" reported cases of Covid-19  in "+search.text.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity,"Please find a valid state in Malaysia", Toast.LENGTH_SHORT).show()
                }

            })
        }




        return view

    }
}


