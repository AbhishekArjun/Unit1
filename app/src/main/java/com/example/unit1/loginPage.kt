package com.example.unit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class loginPage: AppCompatActivity() {
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var login: Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.loginpage)

        email = findViewById(R.id.etEmail)
        pass = findViewById(R.id.etPassword)
        login = findViewById(R.id.btnLogin)
        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passText = pass.text.toString().trim()

            if (emailText.isNotEmpty() || passText.isNotEmpty()) {
                Toast.makeText(this, "Email & Password required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign id Done", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "LoginWrong: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
    }
