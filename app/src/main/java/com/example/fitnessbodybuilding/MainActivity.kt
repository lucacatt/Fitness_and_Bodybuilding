import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.fitnessbodybuilding.*
import com.example.fitnessbodybuilding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Registrazione.OnRegistrationCompleteListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(300)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Controlla solo se savedInstanceState è null per mostrare Introduction
        if (savedInstanceState == null) {
            replaceFragment(Introduction())
        }

        // Imposta il listener per il BottomNavigationView
        binding.bottomNavigationView3.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.workout -> replaceFragment(Workout())
                R.id.diary -> replaceFragment(Diary())
                R.id.profile -> replaceFragment(Profile())
                else -> { }
            }
            true
        }

        // Nasconde il BottomNavigationView durante l'Introduction, la Registrazione e il Login
        hideBottomNavigationViewIfNeeded()
    }

    // Metodo per sostituire i fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    // Nasconde il BottomNavigationView durante l'Introduction, la Registrazione e il Login
    private fun hideBottomNavigationViewIfNeeded() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (currentFragment is Introduction || currentFragment is Registrazione || currentFragment is Login) {
            binding.bottomNavigationView3.visibility = View.GONE
        } else {
            binding.bottomNavigationView3.visibility = View.VISIBLE
        }
    }

    // Metodo chiamato quando la registrazione è completata con successo
    override fun onRegistrationComplete() {
        replaceFragment(Home())
        binding.bottomNavigationView3.visibility = View.VISIBLE
    }
}
