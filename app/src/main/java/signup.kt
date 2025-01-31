package com.example.safeheaven

import SignupRequest
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

class Signup : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginTextView: TextView // Added for login navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        // Initializing the EditText and Button views
        nameEditText = findViewById(R.id.name)
        phoneNumberEditText = findViewById(R.id.phoneNumber)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        signupButton = findViewById(R.id.signupButton)
        loginTextView = findViewById(R.id.loginTextView) // TextView for login

        // Navigate to Login when "Already have an account? Login" is clicked
        loginTextView.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
            finish() // Close Signup activity
        }

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val phone = phoneNumberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Validate input fields
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.length != 10) {
                Toast.makeText(this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Creating SignupRequest object
            val signupRequest = SignupRequest(name, email, password, phone)

            // Call API
            ApiClient.apiService.signup(signupRequest).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Signup, "Signup Successful! Redirecting to Login...", Toast.LENGTH_LONG).show()

                        // Delay to allow user to see the toast message
                        android.os.Handler().postDelayed({
                            val intent = Intent(this@Signup, Login::class.java)
                            startActivity(intent)
                            finish() // Finish the signup activity to prevent going back
                        }, 1500) // Delay for 1.5 seconds
                    } else {
                        Toast.makeText(this@Signup, "Signup Failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@Signup, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
