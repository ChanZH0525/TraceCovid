package com.example.tracecovid.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tracecovid.BaseFragment
import com.example.tracecovid.databinding.FragmentUploadProfilePictureBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadProfilePicture : BaseFragment() {
//    hide Bottom Navigation Bar
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var binding: FragmentUploadProfilePictureBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUploadProfilePictureBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        storageReference = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        binding.imgProfile.setOnClickListener{
            val intent=Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,1)
        }
        binding.btnBack.isEnabled = false
        binding.uploadBtn.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode== AppCompatActivity.RESULT_OK && data!=null && data.data!=null)
        {
            imageUri= data.data!!
            binding.imgProfile.setImageURI(imageUri)
            uploadPic()
        }
    }

    private fun uploadPic() {
        val ref:StorageReference = storageReference.child(auth.currentUser!!.uid+".jpg")
        ref.downloadUrl.addOnSuccessListener {
            var url: String = ref.toString()
        }
        ref.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show()
            binding.uploadBtn.isEnabled = true
        }
            .addOnFailureListener{
                Toast.makeText(context, "Upload Fail", Toast.LENGTH_SHORT).show()
            }
    }
}