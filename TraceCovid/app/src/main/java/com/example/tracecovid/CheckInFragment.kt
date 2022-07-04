package com.example.tracecovid

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.data.CheckInHistory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


class CheckInFragment : Fragment() {

    var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private val DATABASEURL = "https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private lateinit var userId: String
    private lateinit var storageReference: StorageReference
    private lateinit var user: ProfileData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkin, container, false)

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid.toString()

        firebaseDB = FirebaseDatabase.getInstance(DATABASEURL)
        dbreference = firebaseDB.getReference("Users")

//        TODO: handle profile image not found exception
        storageReference = FirebaseStorage.getInstance().reference.child("$userId.jpg")
        val localfile = File.createTempFile("tempImage","jpg")

        // handle for profile information
        val btnCheckInHistory: ImageView = view.findViewById(R.id.btn_check_in_history)
        val profileImage: CircleImageView = view.findViewById(R.id.iv_profile_image_checkin)
        val tvUsername: TextView = view.findViewById(R.id.tv_username_checkin)
        val tvIC: TextView = view.findViewById(R.id.tv_ic_checkin)
        val tvRiskStatus: TextView = view.findViewById(R.id.tv_risk_status)
        val tvSymptomStatus: TextView = view.findViewById(R.id.tv_symptom_status)
        val riskColor:LinearLayout = view.findViewById(R.id.riskColor)
        val btnCheckIn: ExtendedFloatingActionButton = view.findViewById(R.id.extended_fab_check_in)


        storageReference.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            profileImage.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }

        if( userId.isNotEmpty())
        {
            dbreference.child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProfileData::class.java)!!
                    tvUsername.text = user.username
                    tvIC.text = user.ic
                    if (user.risk.isNotEmpty()){
                        tvRiskStatus.text = user.risk
                        tvSymptomStatus.text = user.symptom

                        when (user.risk) {
                            "High Risk" -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
                            }
                            "Medium Risk" -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gold))
                            }
                            else -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.apple_green))
                            }
                        }
                    }
                    else{
//                        TODO: Initialise Status and symptom
                        tvRiskStatus.text = "No Status"
                        riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                    }

                }

                override fun onCancelled(error: DatabaseError) =
                    Toast.makeText(activity, "User Data Cannot Be Load!", Toast.LENGTH_SHORT).show()
            })
        }

        var locations: ArrayList<CheckInHistory> = arrayListOf(
            CheckInHistory(
                "XMUM Malaysia campus",
                "09/05/2022"
            ),
            CheckInHistory(
                "McDonalds",
                "10/05/2022")
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

            checkInIntent.putExtra("userId", userId)
            checkInIntent.putExtra("database", DATABASEURL)
            activity?.startActivity(checkInIntent)
            activity?.finish()
        }
        return view
    }


}