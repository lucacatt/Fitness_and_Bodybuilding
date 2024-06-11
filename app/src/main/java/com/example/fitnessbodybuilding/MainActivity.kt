package com.example.fitnessbodybuilding

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessbodybuilding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Registrazione.OnRegistrationCompleteListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(300)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup NavController with BottomNavigationView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment


        binding.bottomNavigationView3.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.workout -> replaceFragment(Workout())
                R.id.diary -> replaceFragment(Diary())
                R.id.profile -> replaceFragment(Profile())
            }
            true
        }

        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.introductionFragment, R.id.loginFragment, R.id.registrationFragment -> {
                    binding.bottomNavigationView3.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigationView3.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun clearFragmentContent(fragment: Fragment) {
        val view = fragment.view
        if (view is ViewGroup) {
            view.removeAllViews()
        }
    }
    fun replaceFragment(fragment: Fragment) {
        clearFragmentContent(supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    override fun onRegistrationComplete() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.homeFragment)
        binding.bottomNavigationView3.visibility = View.VISIBLE
    }
}
