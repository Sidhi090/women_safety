package com.example.safeheaven

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Get JWT token if needed (Optional)
        val token = intent.getStringExtra("TOKEN")

        welcomeText.text = "Welcome to SafeHeaven!"

        // Handle Logout
        logoutButton.setOnClickListener {
            val intent = Intent(this@Dashboard, Login::class.java)
            startActivity(intent)
            finish() // Finish Dashboard activity so user must re-login
        }
    }
}
