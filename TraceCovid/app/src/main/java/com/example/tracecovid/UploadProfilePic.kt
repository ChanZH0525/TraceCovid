package com.example.tracecovid

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tracecovid.databinding.ActivityUploadProfilePicBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI
import java.util.*

class UploadProfilePic : AppCompatActivity() {
    private lateinit var binding:ActivityUploadProfilePicBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    public lateinit var imageUri:Uri
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUploadProfilePicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage= FirebaseStorage.getInstance()
        storageReference = storage.reference
        auth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, EditProfile::class.java ))
            finish()
        }
        binding.imgProfile.setOnClickListener{
            val intent=Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,1)
        }
        binding.uploadBtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode== RESULT_OK && data!=null && data.data!=null)
        {
            imageUri= data.data!!
            binding.imgProfile.setImageURI(imageUri)
            uploadPic()
        }
    }

    private fun uploadPic() {
        val ref:StorageReference = storageReference.child(auth.currentUser!!.uid+".jpg")
        ref.downloadUrl.addOnSuccessListener {
            var url:String=ref.toString()
        }
        ref.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this, "Success",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Toast.makeText(this, "Fail",Toast.LENGTH_SHORT).show()
            }

    }
}