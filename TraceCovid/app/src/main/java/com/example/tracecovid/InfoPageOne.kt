package com.example.tracecovid

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.adapter.NationalStatisticsAdapter
import com.example.tracecovid.data.CovidData
import com.example.tracecovid.data.ProcessedNationalData
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//class InfoPageOne : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_infopageone)
//
//        val backBtn: ImageView = findViewById(R.id.btn_back_info_page_1)
//        backBtn.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//
//        val toKnowBtn: Button = findViewById(R.id.toKnowBtn)
//        toKnowBtn.setOnClickListener {
//            startActivity(Intent(this, InfoPageTwo::class.java))
//            finish()
//        }
//
//        val linkBtn: ImageButton = findViewById(R.id.img)
//        linkBtn.setOnClickListener {
//
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://themalaysianreserve.com/2021/02/17/highly-anticipated-immunisation-to-start-on-time/")
//            startActivity(intent)
//        }
//    }
//}

class InfoPageOne : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_infopageone, container, false)

        val linkBtn: ImageButton = view.findViewById(R.id.img)
        linkBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://themalaysianreserve.com/2021/02/17/highly-anticipated-immunisation-to-start-on-time/")
            startActivity(intent)
        }
        return view
        }
}