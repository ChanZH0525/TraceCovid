package com.example.tracecovid.checkin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.*
import com.example.tracecovid.R
import com.example.tracecovid.data.CheckInHistory
import com.example.tracecovid.data.ProfileData
import com.example.tracecovid.databinding.FragmentCheckinBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


class CheckInFragment : Fragment() {

    private val TAG: String = "CheckInFragment"
    private var binding: FragmentCheckinBinding? = null
    private lateinit var view: ConstraintLayout
    var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private val DATABASEURL = "https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private lateinit var userId: String
    private lateinit var storageReference: StorageReference
    private lateinit var listener: ValueEventListener
    private lateinit var user: ProfileData
    private lateinit var recyclerViewAdapter: CheckInHistoryAdapter
    private lateinit var recentLocations: ArrayList<CheckInHistory>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCheckinBinding.inflate(inflater, container, false)
        view = binding!!.root

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
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_recent)

        recentLocations = arrayListOf<CheckInHistory>()

        storageReference.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            profileImage.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }

        if(userId.isNotEmpty())
        {
            dbreference = dbreference.child(userId)
            listener = dbreference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProfileData::class.java)!!

                    tvUsername.text = user.username
                    tvIC.text = user.ic
                    if (user.risk.isNotEmpty()){
                        tvRiskStatus.text = user.risk
                        tvSymptomStatus.text = user.symptom

                        when (user.risk) {
                            "High Risk" -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                                    R.color.red
                                ))
                            }
                            "Medium Risk" -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                                    R.color.gold
                                ))
                            }
                            else -> {
                                riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                                    R.color.apple_green
                                ))
                            }
                        }
                    }
                    else{
                        tvRiskStatus.text = "No Status No Symptom"
                        riskColor.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                    }

                }

                override fun onCancelled(error: DatabaseError) =
                    Toast.makeText(activity, "User Data Cannot Be Load!", Toast.LENGTH_SHORT).show()
            })

        }

//        Firebase arrange the checkInHistory in chronological order, thus get the last five(5 most recent)
        dbreference.child("checkInHistory").limitToLast(5).addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val history: CheckInHistory = dataSnapshot.getValue(CheckInHistory::class.java)!!
                    recentLocations.add(history)
                }
                recentLocations.reverse()
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "check in history database reference onCancelled")
            }

        })

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerViewAdapter = CheckInHistoryAdapter(recentLocations)
        recyclerView.adapter = recyclerViewAdapter

        btnCheckInHistory.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_activity_main,
                    AllCheckInHistory.newInstance(userId, DATABASEURL)
                )
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }

        btnCheckIn.setOnClickListener{
//            if you are POSITIVE or (HIGH RISK and WITH SYMPTOM), you are not allowed to check-in
            if ((user.risk == "High Risk" && user.symptom == "With Symptom") || user.symptom == "(Positive)")
            {
                Snackbar.make(view, "You are not allowed to check-in", Snackbar.LENGTH_LONG).show()
            }
            else if(user.risk.isEmpty() || user.symptom.isEmpty()){
                Snackbar.make(view, "Please go to Home to do Risk Assessment", Snackbar.LENGTH_LONG).show()
            }
            else{ //navigate to QR code scan
                var checkInIntent = Intent(context, CheckInActivity::class.java)

                checkInIntent.putExtra("userId", userId) //pass userId
                checkInIntent.putExtra("database", DATABASEURL) //pass database reference
                activity?.startActivity(checkInIntent)
                activity?.finish()
            }

        }
        return view
    }

    override fun onStop() {
        super.onStop()
        dbreference.removeEventListener(listener)
    }
}