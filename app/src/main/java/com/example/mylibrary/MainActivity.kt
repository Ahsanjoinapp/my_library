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
        val prefs = EasyPrefs(this)
        // Save values
        prefs.save("user_name" to "Rahul")
        prefs.save("age" to 25)
        prefs.save("is_logged_in" to true)
        prefs.save("height" to 175.5f)  // Example of Float
        prefs.save("timestamp" to System.currentTimeMillis())  // Example of Long

        // Retrieve values
        val name = prefs.get("user_name", "")
        val age = prefs.get("age", 0)
        val isLoggedIn = prefs.get("is_logged_in", false)
        val height = prefs.get("height", 0f)
        val timestamp = prefs.get("timestamp", 0L)

        prefs.saveObject("user",User("Ahsan",20))

        val user = prefs.getObject<User>("user")

        Log.d("EasyPrefsTest", """
            Name: ${user?.name}
            Age: ${user?.age}
            LoggedIn: $isLoggedIn
            Height: $height
            Timestamp: $timestamp
        """.trimIndent())




        // Delete a key
        prefs.delete("user_name")
    }
}