package com.example.firebasephoneauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var spinner: Spinner? = null
    private var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinnerCountries)
        spinner.setAdapter(
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames
            )
        )
        editText = findViewById(R.id.editTextPhone)
        findViewById<View>(R.id.buttonContinue).setOnClickListener(View.OnClickListener {
            val code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()]
            val number = editText.getText().toString().trim { it <= ' ' }
            if (number.isEmpty() || number.length < 10) {
                editText.setError("Valid number is required")
                editText.requestFocus()
                return@OnClickListener
            }
            val phoneNumber = "+$code$number"
            val intent = Intent(this@MainActivity, VerifyPhoneActivity::class.java)
            intent.putExtra("phonenumber", phoneNumber)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}