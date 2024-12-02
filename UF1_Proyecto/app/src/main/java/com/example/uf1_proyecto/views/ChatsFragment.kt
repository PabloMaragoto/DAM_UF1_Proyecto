package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.adapters.AdapterUsuario
import com.example.uf1_proyecto.databinding.FragmentChatsBinding
import com.example.uf1_proyecto.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatsFragment : Fragment() {

    var _binding: FragmentChatsBinding? = null
    val binding: FragmentChatsBinding get() = _binding!!

    private lateinit var listaUsuarios: ArrayList<Usuario>
    private lateinit var adapterUsuario: AdapterUsuario

    private lateinit var mDbRef : DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("usuarios")

        listaUsuarios = ArrayList()
        setUpRecyclerView()

        return view
    }

    private fun setUpRecyclerView(){

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.usuariosRecyclerView.addItemDecoration(decoration)
        binding.usuariosRecyclerView.layoutManager = layoutManager
        adapterUsuario = AdapterUsuario(requireContext(), listaUsuarios)
        binding.usuariosRecyclerView.adapter = adapterUsuario

        mDbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                listaUsuarios.clear()
                for(postSnapshot in snapshot.children){
                    val usuarioActual = postSnapshot.getValue(Usuario::class.java)

                    if(mAuth.currentUser?.uid != usuarioActual?.uid){
                        listaUsuarios.add(usuarioActual!!)
                    }


                }
                adapterUsuario.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}