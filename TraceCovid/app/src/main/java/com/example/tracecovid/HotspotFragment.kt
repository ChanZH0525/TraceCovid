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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.findFragment
import com.example.tracecovid.databinding.FragmentHotspotBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HotspotFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference
    private  lateinit var firebaseDB: FirebaseDatabase
    private lateinit var binding: FragmentHotspotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB= FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference=firebaseDB.getReference("Hotspot")
        binding = FragmentHotspotBinding.inflate(inflater, container, false)
        var cases:HotpotData


        binding.searchBtn.setOnClickListener{
            dbreference.child(binding.idSearchView.text.toString()).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (binding.idSearchView.text.toString()!="Selangor" &&
                        binding.idSearchView.text.toString()!="Johor" &&
                        binding.idSearchView.text.toString()!="Kuala Lumpur"&&
                        binding.idSearchView.text.toString()!="Terrenganu" &&
                        binding.idSearchView.text.toString()!="Malacca" &&
                        binding.idSearchView.text.toString()!="Negeri Sembilan" &&
                        binding.idSearchView.text.toString()!="Kedah" &&
                        binding.idSearchView.text.toString()!="Kelantan" &&
                        binding.idSearchView.text.toString()!="Labuan" &&
                        binding.idSearchView.text.toString()!="Pahang" &&
                        binding.idSearchView.text.toString()!="Perlis" &&
                        binding.idSearchView.text.toString()!="Sabah" &&
                        binding.idSearchView.text.toString()!="Sarawak" &&
                        binding.idSearchView.text.toString()!="Putrajaya")
                    {
                        Toast.makeText(activity,"Please find a valid state in Malaysia", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        cases = snapshot.getValue(HotpotData::class.java)!! //crashes if entered search does not exist
                        binding.reportedcasesTxt.setText("There are " + cases.Cases + " reported cases of Covid-19  in " + binding.idSearchView.text.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }




        return binding.root

    }
}


