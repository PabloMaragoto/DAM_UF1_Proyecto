package com.example.uf1_proyecto.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.uf1_proyecto.R

import com.example.uf1_proyecto.models.Usuario
import com.example.uf1_proyecto.views.ChatsFragmentDirections
import com.google.firebase.auth.FirebaseAuth


class AdapterUsuario(
    val context: Context,
    val listaUsuarios: ArrayList<Usuario>
): RecyclerView.Adapter<AdapterUsuario.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)
        val ivIconoUsuario= itemView.findViewById<ImageView>(R.id.ivIconoUsuario)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]

        holder.tvUsername.text = usuario.name

        holder.itemView.setOnClickListener{ view ->
            val action = ChatsFragmentDirections.actionChatsFragmentToChatPrivadoFragment(usuario.name, usuario.uid)
            view.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

}