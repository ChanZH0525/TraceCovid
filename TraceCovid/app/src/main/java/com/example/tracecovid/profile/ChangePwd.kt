package com.example.tracecovid.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.tracecovid.BaseFragment
import com.example.tracecovid.Login
import com.example.tracecovid.R
import com.example.tracecovid.databinding.FragmentChangePwdBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class ChangePwd : BaseFragment() {
    //    hide Bottom Navigation Bar
    override var bottomNavigationViewVisibility = View.GONE

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        var binding = FragmentChangePwdBinding.inflate(inflater, container, false)
        val view = binding.root
        val currentPassword:TextInputEditText= view.findViewById(R.id.curpassword)
        val newPassword:TextInputEditText=view.findViewById(R.id.newpassword)
        val confirmPassword: TextInputEditText=view.findViewById(R.id.confirmpassword)


        val btnUpdate: Button = view.findViewById(R.id.updateBtn)
        btnUpdate.setOnClickListener {
           if(currentPassword.text!!.isNotEmpty() && newPassword.text!!.isNotEmpty() && confirmPassword.text!!.isNotEmpty()){
                if (newPassword.text.toString() == confirmPassword.text.toString() && newPassword.length() >= 8)
                {
                    val user = auth.currentUser
                    if(user != null && user.email != null)
                    {
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, currentPassword.text.toString())

                        user.reauthenticate(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful)
                                {
                                    Snackbar.make(view, "Reauthentication Successful", Snackbar.LENGTH_SHORT).show()

                                    user!!.updatePassword(newPassword.text.toString())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(activity,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                                                auth.signOut()
                                                startActivity(Intent(view.context, Login::class.java))
                                                activity?.finish()
                                            }
                                        }
                                }
                                else
                                {
                                    Snackbar.make(view, "Reauthentication Failed", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else
                    {
                        startActivity(Intent(view.context, Login::class.java))
                    }
                }
               else
                {
                    Snackbar.make(view,"New password does not match, password must have a minimum of 8 characters",Snackbar.LENGTH_INDEFINITE).show()
                }
           }
            else{
                Snackbar.make(view,"Fill in all fields",Snackbar.LENGTH_SHORT).show()
            }

        }

            val btnBack: ImageView = view.findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            return view
        }
    }
