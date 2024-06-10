package com.example.fitnessbodybuilding

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.fitnessbodybuilding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(300)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView3.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.workout -> replaceFragment(Workout())
                R.id.diary -> replaceFragment(Diary())
                R.id.profile -> replaceFragment(Profile())
                else -> { }
            }
            true
        }

        // Assicurati che il bottone venga trovato correttamente
        val registrationButton = findViewById<Button>(R.id.a)
        if (registrationButton != null) {
            registrationButton.setOnClickListener {
                replaceFragment(Registrazionee())
            }
        } else {
            println("Button with ID 'a' not found")
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}