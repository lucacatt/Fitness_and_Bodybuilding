package com.example.fitnessbodybuilding

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registrazionee : AppCompatActivity() {
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

        findViewById<Button>(R.id.btnConferma).setOnClickListener {
            try {
                if (checkTexts()) {
                    val user = User(
                        id = dataManagement.utenti.size + 1,
                        username = findViewById<EditText>(R.id.txtUser).text.toString(),
                        password = findViewById<EditText>(R.id.txtPass).text.toString(),
                        email = findViewById<EditText>(R.id.txtEmail).text.toString()
                    )
                    dataManagement.insertUser(user)
                    Toast.makeText(this, "Registrazione avvenuta", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Campi mancanti o errati", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                println("Insertion failed: ${e.message}")
            }
        }
    }

    fun checkTexts(): Boolean {
        return !findViewById<EditText>(R.id.txtEmail).text.toString()
            .isBlank() && !findViewById<EditText>(R.id.txtUser).text.toString()
            .isBlank() && !findViewById<EditText>(R.id.txtPass).text.toString().isBlank()
    }
}