package com.example.uf1_proyecto.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.FragmentSignUpBinding
import com.example.uf1_proyecto.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpFragment : Fragment() {

    var _binding: FragmentSignUpBinding? = null
    val binding: FragmentSignUpBinding get() = _binding!!

    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance()

        edtUsername = binding.edtName
        edtEmail = binding.edtEmail
        edtPassword = binding.edtPassword
        btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener{
            val username = edtUsername.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(username, email, password)

        }

        return view
    }

    private fun signUp(username:String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(username, email, mAuth.currentUser?.uid!!)

                    val action = SignUpFragmentDirections.actionSignUpFragmentToContentFragment()
                    binding.root.findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.errorSignUp), Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(username: String, email: String, uid: String) {

        mDbRef = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("usuarios")

        mDbRef.child(uid).setValue(Usuario(username, email, uid))
            .addOnSuccessListener {
                Log.d("SignUp", "Usuario agregado con UID: $uid")
            }
            .addOnFailureListener { exception ->
                Log.e("SignUp", "Error al agregar usuario: ${exception.message}")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}