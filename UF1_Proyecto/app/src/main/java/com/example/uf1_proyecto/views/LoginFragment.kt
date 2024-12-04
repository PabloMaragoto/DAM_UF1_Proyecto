package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding get() = _binding!!

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    //Iniciamos la clase FirabaseAuth, que contiene los métodos que utiloizaremos para autenticar al usuario
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance()

        edtEmail = binding.edtEmail
        edtPassword = binding.edtPassword
        btnLogin = binding.btnLogin
        btnSignUp = binding.btnSignUp


        btnSignUp.setOnClickListener{
            val navController = view.findNavController()
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            navController.navigate(action)
        }

        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }

        return view
    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val action = LoginFragmentDirections.actionLoginFragmentToContentFragment()
                    binding.root.findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.errorLogIn), Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}