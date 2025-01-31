package com.example.safeheaven

import LoginRequest
import LoginResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        signupLink = findViewById(R.id.signupLink)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validate input fields
            if (email.isNotEmpty() && password.isNotEmpty()) {

                // Creating LoginRequest object
                val loginRequest = LoginRequest(email, password)

                ApiClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()

                            // Navigate to Dashboard
                            val intent = Intent(this@Login, Dashboard::class.java)
                            startActivity(intent)
                            finish() // Finish login activity
                        } else {
                            Toast.makeText(this@Login, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@Login, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@Login, "Please enter valid email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to Signup Page
        signupLink.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
    }
}
