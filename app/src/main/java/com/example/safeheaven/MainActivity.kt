package com.example.safeheaven

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.safeheaven.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay for 2 seconds before navigating to LoginActivity
        Handler().postDelayed({
            // Intent to navigate to LoginActivity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Finish MainActivity so the user cannot go back
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}
