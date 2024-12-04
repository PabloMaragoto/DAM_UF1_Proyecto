package com.example.uf1_proyecto.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.FragmentLogoutBinding
import com.google.firebase.auth.FirebaseAuth


class LogoutFragment : Fragment() {

    var _binding : FragmentLogoutBinding? = null
    val binding: FragmentLogoutBinding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogoutBinding.inflate(inflater,container,false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()

        mAuth.signOut()
        Log.d("Logout", "mAuth.signOut() llamado") // Log adicional

        // Verificar si la sesión se cerró correctamente
        val currentUser = mAuth.currentUser
        Log.d("Logout", "Usuario actual después de signOut: $currentUser") // Log adicional

        if (currentUser == null) {
            Log.d("Logout", "Sesión cerrada con éxito")


        } else {
            Log.d("Logout", "No se pudo cerrar sesión")
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}