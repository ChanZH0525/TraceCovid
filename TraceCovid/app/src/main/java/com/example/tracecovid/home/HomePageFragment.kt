package com.example.tracecovid.home

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.tracecovid.*
import com.example.tracecovid.data.CovidData
import com.example.tracecovid.data.ProfileData
import com.example.tracecovid.databinding.FragmentCheckinBinding
import com.example.tracecovid.databinding.FragmentHomepageBinding
import com.example.tracecovid.home.faq.FAQ
import com.example.tracecovid.home.info.InfoPage
import com.example.tracecovid.home.riskassessment.RiskAssessmentActivity
import com.example.tracecovid.home.selfreport.SelfReport
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList


class HomePageFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    lateinit var binding: FragmentHomepageBinding
    lateinit var view: ScrollView

    lateinit var user: ProfileData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  FragmentHomepageBinding.inflate(inflater, container, false)
        view = binding!!.root


//      Home Fab handle
        val btnRiskAssessment: LinearLayout = view.findViewById(R.id.btn_risk_asess)
        val btnFAQ: LinearLayout = view.findViewById(R.id.btn_faq)
        val btnInfo: LinearLayout = view.findViewById(R.id.btn_info)
        val btnSelfReport: LinearLayout = view.findViewById(R.id.btn_self_report)

//        TabLayout and ViewPager2 handle
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager_statistics)

        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){ tab,index ->
            tab.text = when(index){
                0 -> {"National"}
                1 -> {"International"}
                else -> {throw Resources.NotFoundException("Position Not Found")}
            }
        }.attach()

        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid.toString()
        val firebaseDB = FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val dbReference = firebaseDB.getReference("Users")


//        get user profile to retrieve symptom to determine whether user is positive
        if( userId.isNotEmpty()) {
            dbReference.child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProfileData::class.java)!!
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        btnRiskAssessment.setOnClickListener {
            requireActivity().run {
                if (user.symptom == "(Positive)"){
                    Snackbar.make(view, "Risk Assessment NOT applicable to positive user ", Snackbar.LENGTH_INDEFINITE).show()
                }
                else{
                    startActivity(Intent(view.context, RiskAssessmentActivity::class.java))
                    finish()
                }
            }
        }

        btnFAQ.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(view.context, FAQ::class.java))
                finish()
            }
        }

        btnInfo.setOnClickListener{
            requireActivity().run{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, InfoPage())
                    .addToBackStack(null)
                    .commit()
            }
        }

        btnSelfReport.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(view.context, SelfReport::class.java))
                finish()
            }
        }
        return view
    }
}



