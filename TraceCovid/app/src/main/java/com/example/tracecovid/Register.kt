package com.example.tracecovid

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.tracecovid.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val countries = arrayOf("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",

        "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",

        "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",

        "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",

        "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",

        "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",

        "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",

        "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",

        "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

        "Denmark", "Djibouti", "Dominica", "Dominican Republic",

        "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

        "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

        "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

        "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

        "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

        "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

        "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

        "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

        "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

        "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

        "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

        "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

        "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

        "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

        "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

        "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

        "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

        "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

        "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

        "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

        "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

        "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

        "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

        "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

        "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener{
            val email=binding.regemail.text.toString()
            val pwd=binding.regpwd.text.toString()
            val pwd2=binding.regpwd2.text.toString()

            if(email.isNotEmpty()&&pwd.isNotEmpty()&&pwd2.isNotEmpty())
            {
                if(pwd==pwd2){
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            startActivity(Intent(this, Login::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else
                {
                    Toast.makeText(this,"Password does not match",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this,"Fill in all details",Toast.LENGTH_SHORT).show()
            }


        }

        val username:TextInputLayout=findViewById(R.id.name)
        val nationality: TextInputLayout=findViewById(R.id.nationality)
        val ic: TextInputLayout=findViewById(R.id.ic)
        val state: TextInputLayout=findViewById(R.id.state)
        val password: TextInputLayout=findViewById(R.id.password)
        val confirmpass:TextInputLayout=findViewById(R.id.confirmpassword)




        val backBtn: ImageView = findViewById(R.id.btn_back_register)
        backBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

     //   val signUpBtn: Button = findViewById(R.id.signupBtn)
       // signUpBtn.setOnClickListener{


      //  }

    }





    override fun onResume(){
        super.onResume()

        val dropdown_country = findViewById<AutoCompleteTextView>(R.id.dropdown_country)
        //val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter_country = ArrayAdapter(this, R.layout.dropdown_list, countries)
        dropdown_country.setAdapter(arrayAdapter_country)

        val dropdown_state = findViewById<AutoCompleteTextView>(R.id.dropdown_state)
        val states = resources.getStringArray(R.array.states)
        val arrayAdapter_state = ArrayAdapter(this, R.layout.dropdown_list, states)
        dropdown_state.setAdapter(arrayAdapter_state)
    }





}