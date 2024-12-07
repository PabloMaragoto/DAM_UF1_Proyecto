package com.example.uf1_proyecto.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
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

        //Accede al Toolbar a través de la actividad
        val toolbar = binding.toolbar

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Setup NavController
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup AppBarConfiguration
        val drawerLayout = binding.drawerLayout
        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawerLayout)
        val appBarConfiguration = builder.build()

        // Configura el Toolbar con el NavController y AppBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Setup BottomNavigationView
        //val bottombar = binding.bottomNavigation
        //bottombar.setupWithNavController(navController)

        // Setup NavigationView
        val navigationView = binding.navView
        navigationView.setupWithNavController(navController)


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}