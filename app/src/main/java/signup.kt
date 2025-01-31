package com.example.safeheaven

import SignupRequest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        // Button click logic
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneNumberEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validating input fields
            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password == confirmPassword) {

                // Check if phone number is valid
                if (phone.length != 10) {
                    Toast.makeText(this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Check if email is valid
                if (!email.contains("@")) {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Check if passwords match
                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Check if password length is sufficient
                if (password.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Creating SignupRequest object
                val signupRequest = SignupRequest(name, email, password, phone)

                // Making the API call
                ApiClient.apiService.signup(signupRequest).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            // Handle success
                            Toast.makeText(this@Signup, "Signup Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Signup, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle error
                            Toast.makeText(this@Signup, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // Handle failure
                        Toast.makeText(this@Signup, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                // Handle case where fields are missing or passwords don't match
                Toast.makeText(this, "Please check your fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
