package com.example.mylibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Save values
        save("user_name" to "Rahul")
        save("age" to 25)
        save("is_logged_in" to true)
        save("height" to 175.5f)  // Example of Float
        save("timestamp" to System.currentTimeMillis())  // Example of Long

        // Retrieve values
        val name = get("user_name", "")
        val age = get("age", 0)
        val isLoggedIn = get("is_logged_in", false)
        val height = get("height", 0f)
        val timestamp = get("timestamp", 0L)

        Log.d("EasyPrefsTest", """
            Name: $name
            Age: $age
            LoggedIn: $isLoggedIn
            Height: $height
            Timestamp: $timestamp
        """.trimIndent())

        // Delete a key
        delete("user_name")
    }
}