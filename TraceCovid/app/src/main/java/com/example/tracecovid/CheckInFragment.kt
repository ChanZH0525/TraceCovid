package com.example.tracecovid

import android.app.Application
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView


class CheckInFragment : Fragment() {

    var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private val DATABASEURL = "https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private lateinit var uid: String
    private lateinit var user: ProfileData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkin, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        firebaseDB = FirebaseDatabase.getInstance(DATABASEURL)
        dbreference = firebaseDB.getReference("Users")
        // handle for profile information
        val btnCheckInHistory: ImageView = view.findViewById(R.id.btn_check_in_history)
        val profileImage: CircleImageView = view.findViewById(R.id.iv_profile_image_checkin)
        val tvUsername: TextView = view.findViewById(R.id.tv_username_checkin)
        val tvIC: TextView = view.findViewById(R.id.tv_ic_checkin)
        val tvRiskStatus: TextView = view.findViewById(R.id.tv_risk_status)
        val tvSymptomStatus: TextView = view.findViewById(R.id.tv_symptom_status)
        val riskColor:LinearLayout = view.findViewById(R.id.riskColor)
        val btnCheckIn: ExtendedFloatingActionButton = view.findViewById(R.id.extended_fab_check_in)

        if( uid.isNotEmpty())
        {
            dbreference.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProfileData::class.java)!!
                    tvUsername.text = user.username
                    tvIC.text = user.ic
                    tvRiskStatus.text = user.risk
                    tvSymptomStatus.text = user.symptom

                    if(user.risk == "High Risk"){
                        riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
                    }
                    else if(user.risk == "Medium Risk"){
                        riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gold))
                    }
                    else{
                        riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.apple_green))
                    }
                }

                override fun onCancelled(error: DatabaseError) =
                    Toast.makeText(activity, "User Data Cannot Be Load!", Toast.LENGTH_SHORT).show()
            })
        }

        var locations: ArrayList<CheckInHistory> = arrayListOf(
            CheckInHistory(
                "XMUM Malaysia campus",
                "09/05/2022",
                "12:00:00"
            ),
            CheckInHistory(
                "McDonalds",
                "10/05/2022",
                "14:14:20")
        )

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_recent)

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = CheckInHistoryAdapter(locations)

        btnCheckInHistory.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, AllCheckInHistory())
                .addToBackStack(null)
                .commit()
        }

        btnCheckIn.setOnClickListener{
            var checkInIntent = Intent(context, CheckInActivity::class.java)
            checkInIntent.putExtra("userId", uid)
            checkInIntent.putExtra("database", DATABASEURL)
            activity?.startActivity(checkInIntent)
            activity?.finish()
        }
        return view
    }


}