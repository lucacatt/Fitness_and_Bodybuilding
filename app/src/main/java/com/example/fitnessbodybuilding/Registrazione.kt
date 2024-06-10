package com.example.fitnessbodybuilding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registrazione : AppCompatActivity() {
    private lateinit var dataManagement: DataManagement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrazione)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dataManagement = DataManagement()
        try {
            val user = User(
                id = dataManagement.utenti.size + 1,
                username = "MarioRossi",
                password = "password123",
                email = "mario.rossexample.com"
            )
            dataManagement.insertUser(user)
        } catch (e: IllegalArgumentException) {
            println("Insertion failed: ${e.message}")
        }
    }
}