package com.example.uf1_proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.adapters.AdapterMensaje
import com.example.uf1_proyecto.databinding.FragmentChatPrivadoBinding
import com.example.uf1_proyecto.models.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatPrivadoFragment : Fragment() {

    var _binding: FragmentChatPrivadoBinding? = null
    val binding: FragmentChatPrivadoBinding get() = _binding!!

    private lateinit var cajaMensaje: EditText
    private lateinit var btnEnviar: Button

    private lateinit var adapterMensaje: AdapterMensaje
    private lateinit var  listaMensajes: ArrayList<Mensaje>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatPrivadoBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val args = ChatPrivadoFragmentArgs.fromBundle(requireArguments())

        val username = args.username
        val receiverUid = args.uid

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance("https://mispeliculasapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        cajaMensaje = binding.edtMessage
        btnEnviar = binding.btnSend

        listaMensajes = ArrayList()

        setUpRecyclerView()

        btnEnviar.setOnClickListener{
            val mensaje = cajaMensaje.text.toString()
            val mensajeObject = Mensaje(mensaje, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("mensajes").push().setValue(mensajeObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("mensajes").push().setValue(mensajeObject)
            }

            cajaMensaje.setText("")
        }
        return view
    }

    private fun setUpRecyclerView(){

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerChat.layoutManager = layoutManager
        adapterMensaje = AdapterMensaje(requireContext(), listaMensajes)
        binding.recyclerChat.adapter = adapterMensaje

        mDbRef.child("chats").child(senderRoom!!).child("mensajes").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                listaMensajes.clear()

                for(postSnapshot in snapshot.children){
                    val mensaje = postSnapshot.getValue(Mensaje::class.java)
                    listaMensajes.add(mensaje!!)
                }
                adapterMensaje.notifyDataSetChanged()
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