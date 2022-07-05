package com.example.tracecovid

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.data.CheckInHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "userId"
private const val ARG_PARAM2 = "database"

class AllCheckInHistory : Fragment() {
    private val TAG: String = "AllCheckInHistory"
    private var userId: String? = null
    private var database: String? = null
    private lateinit var dbReference: DatabaseReference
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var recyclerViewAdapter: CheckInHistoryAdapter
    private lateinit var listener: ValueEventListener
    var recentLocations: ArrayList<CheckInHistory> = arrayListOf()
    var allLocations: ArrayList<CheckInHistory> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(ARG_PARAM1)
            database = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_check_in_history, container, false)

        val backbtn: ImageView = view.findViewById(R.id.btn_back_check_in)
        val weekhistbtn: Button = view.findViewById(R.id.lastweekbtn)
        val allhistbtn: Button = view.findViewById(R.id.allhistbtn)

        firebaseDB = FirebaseDatabase.getInstance(database.toString())
        dbReference = firebaseDB.getReference("Users")

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recyler_view_check_in_hist)
        backbtn.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        if(userId!!.isNotEmpty()){
            listener = dbReference.child(userId.toString()).child("checkInHistory").addValueEventListener(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children){
                        val history: CheckInHistory = dataSnapshot.getValue(CheckInHistory::class.java)!!

                        allLocations.add(history)
                    }
//                    In reverse chronological order
                    allLocations.reverse()
                    recyclerViewAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "check in history database reference onCancelled")
                }

            })
        }
        weekhistbtn.setOnClickListener {
            weekhistbtn.isSelected = true
            allhistbtn.isSelected = false



            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerViewAdapter = CheckInHistoryAdapter(recentLocations)
            recyclerView.adapter = recyclerViewAdapter
        }
        weekhistbtn.performClick()


        allhistbtn.setOnClickListener {
            allhistbtn.isSelected = true
            weekhistbtn.isSelected = false

            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerView.adapter = CheckInHistoryAdapter(allLocations)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String, database: String) =
            AllCheckInHistory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, userId)
                    putString(ARG_PARAM2, database)
                }
            }
    }

//
}