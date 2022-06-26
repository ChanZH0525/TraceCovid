package com.example.tracecovid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditProfile : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private val countries = arrayOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "American Samoa",
        "Andorra",
        "Angola",
        "Anguilla",

        "Antarctica",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",

        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",

        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",

        "Brazil",
        "British Indian Ocean Territory",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",

        "Burkina Faso",
        "Burma (Myanmar)",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",

        "Cayman Islands",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Christmas Island",

        "Cocos (Keeling) Islands",
        "Colombia",
        "Comoros",
        "Cook Islands",
        "Costa Rica",

        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Democratic Republic of the Congo",

        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",

        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",

        "Ethiopia",
        "Falkland Islands",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Polynesia",

        "Gabon",
        "Gambia",
        "Gaza Strip",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",

        "Greenland",
        "Grenada",
        "Guam",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",

        "Haiti",
        "Holy See (Vatican City)",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",

        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Ivory Coast",
        "Jamaica",

        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kosovo",
        "Kuwait",

        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",

        "Lithuania",
        "Luxembourg",
        "Macau",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",

        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Mexico",

        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",

        "Mozambique",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",

        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Niue",
        "Norfolk Island",
        "North Korea",

        "Northern Mariana Islands",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",

        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Pitcairn Islands",
        "Poland",

        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Republic of the Congo",
        "Romania",
        "Russia",
        "Rwanda",

        "Saint Barthelemy",
        "Saint Helena",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Martin",

        "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines",
        "Samoa",
        "San Marino",

        "Sao Tome and Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",

        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Korea",

        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",

        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Timor-Leste",
        "Togo",
        "Tokelau",

        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks and Caicos Islands",

        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "Uruguay",
        "US Virgin Islands",
        "Uzbekistan",
        "Vanuatu",
        "Venezuela",
        "Vietnam",

        "Wallis and Futuna",
        "West Bank",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )
    lateinit var database: FirebaseDatabase
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference
    lateinit var username: TextInputEditText
    lateinit var dropdown_country: AutoCompleteTextView
    lateinit var dropdown_state: AutoCompleteTextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database =FirebaseDatabase.getInstance("https://tracecovid-e507a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbreference = database.getReference("Users").child(uid)
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val btnBack: ImageView = view.findViewById(R.id.btn_back)
        val saveBtn: Button =view.findViewById(R.id.saveBtn)
        val selectBtn: Button= view.findViewById(R.id.selectBtn)
        username = view.findViewById(R.id.change_username)
        dropdown_country = view.findViewById<AutoCompleteTextView>(R.id.dropdown_country)
        val arrayAdapter_country = view.let { ArrayAdapter(it.context, R.layout.dropdown_list, countries) }
        dropdown_country?.setAdapter(arrayAdapter_country)

        dropdown_state = view.findViewById<AutoCompleteTextView>(R.id.dropdown_state)
        val states = resources.getStringArray(R.array.states)
        val arrayAdapter_state = view.let { ArrayAdapter(it.context, R.layout.dropdown_list, states) }
        dropdown_state?.setAdapter(arrayAdapter_state)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        saveBtn.setOnClickListener{
            var uname: String = username.text.toString()
            var nationality: String = dropdown_country.text.toString()
            var newstate :String= dropdown_state.text.toString()


            val map= mapOf(
                "country" to nationality,
                "username" to uname,
                "state" to newstate
            )
            dbreference.updateChildren(map)
            parentFragmentManager.popBackStack()
        }
        selectBtn.setOnClickListener{
            startActivity(Intent(activity, UploadProfilePic::class.java))
        }
        return view
    }


}