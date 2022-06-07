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
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var auth:FirebaseAuth

    private var username:String=""
    private var password:String=""
    private var repassword:String=""



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

       // actionBar=supportActionBar!!
       // actionBar.title="Register"
       // actionBar.setDisplayHomeAsUpEnabled(true)
       // actionBar.setDisplayShowHomeEnabled(true)

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Signing Up")
        progressDialog.setCanceledOnTouchOutside(false)

        auth= FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener{
            validatedata()
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

    private fun validatedata() {
        //still need ic, state, nationality
        username=binding.name.toString().trim()
        password=binding.password.toString().trim()
        repassword=binding.confirmpassword.toString().trim()
      //  if(password!=repassword)
        //{
          //  binding.password.setError("password does not match")
        //}
         if (TextUtils.isEmpty(password))
        {
            binding.password.setError("no password entered")
        }
        else if (password.length<6)
        {
            binding.password.setError("minimum 6 characters for password")
        }
        else
        {
            progressDialog.show()
            auth.createUserWithEmailAndPassword(username,password)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    val firebaseUser=auth.currentUser
                 //   val username=firebaseUser!!.phoneNumber
                    Toast.makeText(this,"Register Success",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

            }.addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(this,"Register Failed",Toast.LENGTH_SHORT).show()
                }
        }

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