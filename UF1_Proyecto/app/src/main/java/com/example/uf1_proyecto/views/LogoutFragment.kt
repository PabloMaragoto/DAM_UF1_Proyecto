package com.example.uf1_proyecto.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.FragmentLogoutBinding
import com.example.uf1_proyecto.viewmodels.SharedViewModel
import com.google.firebase.auth.FirebaseAuth


class LogoutFragment : Fragment() {

    var _binding : FragmentLogoutBinding? = null
    val binding: FragmentLogoutBinding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutBinding.inflate(inflater,container,false)
        val view = binding.root

        sharedViewModel.setBarVisibility(false)

        mAuth = FirebaseAuth.getInstance()

        mAuth.signOut()
        Log.d("Logout", "mAuth.signOut() llamado")

        val currentUser = mAuth.currentUser
        Log.d("Logout", "Usuario actual después de signOut: $currentUser")

        if (currentUser == null) {
            Log.d("Logout", "Successfully logged out")
            val parentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container_view)
            //parentFragment?.setV()
            findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
        } else {
            Log.d("Logout", "Logout failed")
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}