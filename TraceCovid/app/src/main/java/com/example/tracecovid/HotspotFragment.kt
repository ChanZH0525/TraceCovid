package com.example.tracecovid

//import android.widget.SearchView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tracecovid.databinding.FragmentHotspotBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HotspotFragment : BaseFragment(), OnMapReadyCallback {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference
    private  lateinit var firebaseDB: FirebaseDatabase

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotspot, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB =
            FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = firebaseDB.getReference("Hotspot")

        var cases: HotpotData
        val dropdown_states = view.findViewById<AutoCompleteTextView>(R.id.dropdown_states)
        //Google Map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val state = resources.getStringArray(R.array.states)
        val arrayAdapter_states = view?.let { ArrayAdapter(it.context, R.layout.dropdown_list, state) }
        dropdown_states.setAdapter(arrayAdapter_states)



        /*binding.searchBtn.setOnClickListener{
        dbreference.child(binding.idSearchView.text.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (binding.idSearchView.text.toString() != "Selangor" &&
                        binding.idSearchView.text.toString() != "Johor" &&
                        binding.idSearchView.text.toString() != "Kuala Lumpur" &&
                        binding.idSearchView.text.toString() != "Terrenganu" &&
                        binding.idSearchView.text.toString() != "Malacca" &&
                        binding.idSearchView.text.toString() != "Negeri Sembilan" &&
                        binding.idSearchView.text.toString() != "Kedah" &&
                        binding.idSearchView.text.toString() != "Kelantan" &&
                        binding.idSearchView.text.toString() != "Labuan" &&
                        binding.idSearchView.text.toString() != "Pahang" &&
                        binding.idSearchView.text.toString() != "Perlis" &&
                        binding.idSearchView.text.toString() != "Sabah" &&
                        binding.idSearchView.text.toString() != "Sarawak" &&
                        binding.idSearchView.text.toString() != "Putrajaya"
                    ) {
                        Toast.makeText(
                            activity,
                            "Please find a valid state in Malaysia",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        cases =
                            snapshot.getValue(HotpotData::class.java)!! //crashes if entered search does not exist
                        binding.reportedcasesTxt.setText("There are " + cases.Cases + " reported cases of Covid-19  in " + binding.idSearchView.text.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    } */
        return view

    }

    override fun onMapReady( googleMap:GoogleMap) {
        mMap = googleMap

        val location = LatLng(3.1390, 101.6869) //KL

        mMap.addMarker(MarkerOptions().position(location).title("Marker in KL"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        val center = CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude))
        val zoom = CameraUpdateFactory.zoomTo(12f)
        mMap.animateCamera(zoom)
    }
}


