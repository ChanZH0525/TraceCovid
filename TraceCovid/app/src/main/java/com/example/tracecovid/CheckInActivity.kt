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
import com.example.tracecovid.databinding.ActivityCheckInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import java.lang.Exception
import java.util.concurrent.ExecutionException


class CheckInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var layout: View
    private lateinit var previewView: PreviewView
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(layout, "Camera Permission Granted", Snackbar.LENGTH_INDEFINITE).show()
                startCamera()
            }
            else {
                Snackbar.make(layout, "Camera Permission Denied", Snackbar.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckInBinding.inflate(layoutInflater)
        layout = binding.root

        setContentView(layout)

        previewView = findViewById(R.id.previewView)
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
            startCamera()
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

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                bindCameraPreview(cameraProvider)
            } catch (e: ExecutionException) {
                Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT)
                    .show()
            } catch (e: InterruptedException) {
                Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider?) {
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
//        select back camera as a default
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider?.unbindAll()

            cameraProvider?.bindToLifecycle(
                this, cameraSelector, preview
            )
        }catch (exc: Exception)
        {
            exc.printStackTrace()
        }

    }
}