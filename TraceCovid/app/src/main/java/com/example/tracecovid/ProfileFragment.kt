package com.example.tracecovid

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.auth.User
import com.squareup.okhttp.internal.DiskLruCache
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.reflect.Field


class ProfileFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var auth:FirebaseAuth
    private lateinit var firebaseDB:FirebaseDatabase
    private lateinit var dbreference:DatabaseReference
    private lateinit var uid: String
    private lateinit var user:ProfileData
    private lateinit var btnSetting: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()
        uid=auth.currentUser?.uid.toString()
        firebaseDB= FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference=firebaseDB.getReference("Users")
       // handle for profile information
        val profileImage: CircleImageView = view.findViewById(R.id.iv_profile)
        btnSetting = view.findViewById(R.id.btn_setting_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvPhone: TextView = view.findViewById(R.id.tv_phone)
        val tvIC: TextView = view.findViewById(R.id.tv_ic_passport)
        val tvState: TextView = view.findViewById(R.id.tv_state)

        if( uid.isNotEmpty())
        {
            dbreference.child(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user=snapshot.getValue(ProfileData::class.java)!!
                    tvUsername.setText(user.username)
                    tvIC.setText(user.ic)
                    tvPhone.setText(user.phonenumber) //here missing
                    tvState.setText(user.state)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        val popupMenu = PopupMenu(context, btnSetting)
        popupMenu.inflate(R.menu.profile_settings_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit_profile -> {
                    navigateFragment(EditProfile())
                    true
                }
                R.id.update_phone_number -> {
                    navigateFragment(UpdatePhoneNo())
                    true
                }
                R.id.change_password -> {
                    navigateFragment(ChangePwd())
                    true
                }
                R.id.company_info -> {
                    navigateFragment(CompanyInfo())
                    true
                }
                R.id.logout -> {
                    activity?.startActivity(Intent(context, Login::class.java))
                    activity?.finish()
                    true
                }
                else -> true
            }
        }

        btnSetting.setOnClickListener{

            try {
                val popup: Field = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            }catch(e: Exception){
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
        }

        return view
    }



    private fun navigateFragment(nextFragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, nextFragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }
}