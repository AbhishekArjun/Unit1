package com.example.unit1.signUp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.unit1.R
import com.example.unit1.login.SwiggyLogin

class SwiggySignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.swiggysignup)

        val tvLogin = findViewById<TextView>(R.id.btnContinue)
        tvLogin.setOnClickListener {
            startActivity(Intent(this, SwiggyLogin::class.java))
        }
    }
}
