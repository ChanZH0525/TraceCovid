package com.example.tracecovid

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tracecovid.data.CheckInHistory
import com.example.tracecovid.databinding.ActivityCheckInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutionException


class CheckInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var layout: View
    private lateinit var previewView: PreviewView
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var userId: String
    private lateinit var DATABASEURL: String
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var dbReference: DatabaseReference

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Snackbar.make(layout, "Cancelled", Snackbar.LENGTH_LONG).show()
        } else {
            if(userId.isNotEmpty()){
                val currentDatabaseReference = dbReference.child(userId).child("checkInHistory").push()
//                get current time in timezone GMT+08 Asia/Kuala Lumpur
                val checkInDateTime = DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.of("Asia/Kuala_Lumpur"))
                    .format(Instant.now())

                currentDatabaseReference.child("locationName").setValue(result.contents)
                currentDatabaseReference.child("checkInDateTime").setValue(checkInDateTime)
                Toast.makeText(applicationContext, "Checked in " + result.contents, Toast.LENGTH_LONG).show()
                val checkInIntent = Intent(this, MainActivity::class.java)
                checkInIntent.putExtra("Source", "CheckInActivity")
                this.startActivity(checkInIntent)
                this.finish()
            }

        }
    }

    // Launch
    fun onButtonClick(view: View?) {

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission has been granted. Start camera preview Activity.
                Toast.makeText(applicationContext, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                scanQR()
            }
            else {
                Toast.makeText(applicationContext, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckInBinding.inflate(layoutInflater)
        layout = binding.root

        setContentView(layout)

        userId = intent.getStringExtra("userId").toString()
        DATABASEURL = intent.getStringExtra("database").toString()
        firebaseDB = FirebaseDatabase.getInstance(DATABASEURL)
        dbReference = firebaseDB.getReference("Users")

//        previewView = findViewById(R.id.previewView)
        val btnBack: ImageView = findViewById(R.id.btn_back_camera)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

    override fun onStart() {
        super.onStart()

        requestCamera()
    }

    private fun requestCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            scanQR()
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
            {
                Snackbar.make(layout, "Camera Permission Required for Scanning", Snackbar.LENGTH_INDEFINITE).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)

            } else {
                Snackbar.make(layout, "Camera Permission Not Available", Snackbar.LENGTH_INDEFINITE).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun scanQR() {
        var options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan a QR code");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false); //set to false to allow orientation change
        options.captureActivity = CaptureAct::class.java
        barcodeLauncher.launch(options);
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSION_REQUEST_CAMERA) {
//            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera()
//            } else {
//                Snackbar.make(layout, "Camera Permission Denied", Snackbar.LENGTH_INDEFINITE).show()
//                requestCamera()
//            }
//        }
//    }

//    private fun startCamera() {
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            try {
//                val cameraProvider = cameraProviderFuture.get()
//                bindCameraPreview(cameraProvider)
//            } catch (e: ExecutionException) {
//                Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT)
//                    .show()
//            } catch (e: InterruptedException) {
//                Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }

//    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider?) {
//        val preview = Preview.Builder()
//            .build()
//            .also {
//                it.setSurfaceProvider(binding.previewView.surfaceProvider)
//            }
////        select back camera as a default
//        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//        try {
//            cameraProvider?.unbindAll()
//
//            cameraProvider?.bindToLifecycle(
//                this, cameraSelector, preview
//            )
//        }catch (exc: Exception)
//        {
//            exc.printStackTrace()
//        }
//      }
}