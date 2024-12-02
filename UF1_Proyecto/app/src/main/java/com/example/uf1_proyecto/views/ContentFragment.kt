package com.example.uf1_proyecto.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.FragmentContentBinding
import com.google.firebase.auth.FirebaseAuth


class ContentFragment : Fragment() {

    var _binding: FragmentContentBinding? = null

    val binding: FragmentContentBinding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()

        // Accede al Toolbar a través de la actividad
        val toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Setup NavController
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup AppBarConfiguration
        val drawerLayout = binding.drawerLayout
        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawerLayout)
        val appBarConfiguration = builder.build()

        // Configura el Toolbar con el NavController y AppBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Setup BottomNavigationView
        val bottombar = binding.bottomNavigation
        bottombar.setupWithNavController(navController)

        // Setup NavigationView
        val navigationView = binding.navView
        navigationView.setupWithNavController(navController)

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()

        if (item.itemId == R.id.logout) {
            mAuth.signOut()

            if (mAuth.currentUser == null) {
                Log.d("Logout", "Sesión cerrada con éxito")
            } else {
                Log.d("Logout", "No se pudo cerrar sesión")
            }

            navController.setGraph(R.navigation.nav_graph_login)

            navController.navigate(R.id.loginFragment)

            return true
        }

        // No usar  onNavDestinationSelected para logout, ya que no es un destino
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}