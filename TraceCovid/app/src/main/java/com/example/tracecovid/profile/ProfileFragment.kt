package com.example.tracecovid.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.tracecovid.*
import com.example.tracecovid.R
import com.example.tracecovid.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.lang.reflect.Field


class ProfileFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbreference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var uid: String
    private lateinit var user: ProfileData
    private lateinit var btnSetting: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()

        uid = auth.currentUser?.uid.toString()
        firebaseDB = FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = firebaseDB.getReference("Users")
        storageReference = FirebaseStorage.getInstance().reference.child("$uid.jpg")

        val localFile = File.createTempFile("tempImage","jpg")
       // handle for profile information
        val profileImage: CircleImageView = view.findViewById(R.id.iv_profile)
        btnSetting = view.findViewById(R.id.btn_setting_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvPhone: TextView = view.findViewById(R.id.tv_phone)
        val tvIC: TextView = view.findViewById(R.id.tv_ic_passport)
        val tvState: TextView = view.findViewById(R.id.tv_state)
        val tvNationality: TextView = view.findViewById(R.id.tv_nationality)
        if( uid.isNotEmpty())
        {
            dbreference.child(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProfileData::class.java)!!
                    tvUsername.text = user.username
                    tvIC.text = user.ic
                    tvPhone.text = user.phonenumber
                    tvState.text = user.state
                    tvNationality.text = user.country
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            storageReference.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                profileImage.setImageBitmap(bitmap)
            }.addOnFailureListener{

            }

        }

        val popupMenu = PopupMenu(context, btnSetting)
        popupMenu.inflate(R.menu.profile_settings_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit_profile -> {
                    navigateFragment(EditProfile())
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
                    auth.signOut()
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
            } catch(e: Exception){
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