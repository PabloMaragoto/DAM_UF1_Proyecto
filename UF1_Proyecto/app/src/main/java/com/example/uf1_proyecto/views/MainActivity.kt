package com.example.uf1_proyecto.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.ActivityMainBinding
import com.example.uf1_proyecto.viewmodels.PeliculasViewModel
import com.example.uf1_proyecto.viewmodels.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var mAuth: FirebaseAuth

    private lateinit var titulo: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        mAuth = FirebaseAuth.getInstance()

        //Accede al Toolbar a través de la actividad
        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        // Setup NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment), // Lista de destinos raíz
            binding.drawerLayout // Vincula el DrawerLayout
        )

        // Configura el Toolbar con el NavController y AppBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Setup NavigationView
        val navigationView = binding.navView
        navigationView.setupWithNavController(navController)

        val bottombar = binding.bottomNavigation
        bottombar.setupWithNavController(navController)

        sharedViewModel.BarVisible.observe(this) { isVisible ->
            bottombar.visibility = if (isVisible) View.VISIBLE else View.GONE
            toolbar.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Recuperamos el controlador de navegación
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        val navController = navHostFragment.navController


        //Y lo vinculamos con los items del menú
        NavigationUI.onNavDestinationSelected(item, navController)
        return super.onOptionsItemSelected(item)
    }
}

