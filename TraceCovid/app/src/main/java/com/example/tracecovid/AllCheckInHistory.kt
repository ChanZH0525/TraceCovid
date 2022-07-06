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
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.abs

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
    private lateinit var recentRecyclerViewAdapter: CheckInHistoryAdapter
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

    @SuppressLint("SimpleDateFormat")
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

        val currentDate = Date()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
        if(userId!!.isNotEmpty()){
//            get all the children under checkInHistory
            listener = dbReference.child(userId.toString()).child("checkInHistory").addValueEventListener(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children){
                        val history: CheckInHistory = dataSnapshot.getValue(CheckInHistory::class.java)!!
                        allLocations.add(history)
//                        DateTime Formet in database = "dd/MM/yyyy HH:mm:ss"
//                        get date only which is before " "
                        val recordDate = history.checkInDateTime!!.substringBefore(" ")
                        val historyDate = dateFormatter.parse(recordDate)
                        val diffInMS = abs(currentDate.time - historyDate.time)
                        val diff = TimeUnit.DAYS.convert(diffInMS, TimeUnit.MILLISECONDS)

//                       If the difference between the current date and history date is within 7 days
                        if (diff <= 6){
                            recentLocations.add(history)
                        }
                    }
//                    In reverse chronological order
                    allLocations.reverse()
                    recentLocations.reverse()
                    recentRecyclerViewAdapter.notifyDataSetChanged()

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
            recentRecyclerViewAdapter = CheckInHistoryAdapter(recentLocations)
            recyclerView.adapter = recentRecyclerViewAdapter
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
            recyclerViewAdapter = CheckInHistoryAdapter(allLocations)
            recyclerView.adapter = recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
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