package com.example.unit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FireBase : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnreg)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Email & Password required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->


                    if (task.isSuccessful) {
                        sendVerificationEmail()
                    } else {
                        Toast.makeText(
                            this,
                            "Registration Failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }


    private fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Verification Email Sent. Please verify & login.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun loginUser() {
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Please verify your email first",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
