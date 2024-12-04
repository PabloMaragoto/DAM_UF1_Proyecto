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

        val currentUser = mAuth.currentUser

        if(currentUser == null){
            bottombar.visibility = View.GONE
        }else{
            bottombar.visibility = View.VISIBLE
        }

        return view
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Logout", "onOptionsItemSelected llamado")
        val navController = findNavController()

        Log.d("Logout", "Item seleccionado: ${item.itemId}")

        if (item.itemId == R.id.logoutFragment) {
            Log.d("Logout", "Opción de logout seleccionada")

            // Cerrar sesión
            mAuth.signOut()
            Log.d("Logout", "mAuth.signOut() llamado")

            val currentUser = mAuth.currentUser
            Log.d("Logout", "Usuario actual después de signOut: $currentUser")

            if (currentUser == null) {
                Log.d("Logout", "Sesión cerrada con éxito")

                navController.navigate(R.id.logoutFragment)

                navController.setGraph(R.navigation.nav_graph_login)

                navController.navigate(R.id.loginFragment)
            } else {
                Log.d("Logout", "No se pudo cerrar sesión")
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }*/

    fun setVisibilityBar(){
        binding.bottomNavigation.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}