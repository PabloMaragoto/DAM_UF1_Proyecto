package com.example.uf1_proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.models.Mensaje
import com.google.firebase.auth.FirebaseAuth

class AdapterMensaje(
    val context: Context,
    val listaMensajes: ArrayList<Mensaje>
): RecyclerView.Adapter<ViewHolder>() {

    val ITEM_RECIBIDO = 1
    val ITEM_ENVIADO = 2

    class EnviadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mensajeEnviado = itemView.findViewById<TextView>(R.id.txt_mensaje_enviado)

    }

    class RecibidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mensajeRecibido = itemView.findViewById<TextView>(R.id.txt_mensaje_recibido)
    }

    override fun getItemViewType(position: Int): Int {
        val mensajeActual = listaMensajes[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(mensajeActual.senderId)){
            return ITEM_ENVIADO
        }else{
            return ITEM_RECIBIDO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == ITEM_ENVIADO){
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_mensaje_enviado, parent, false)
            return EnviadoViewHolder(view)
        }else{
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_mensaje_recibido, parent, false)
            return RecibidoViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mensajeActual = listaMensajes[position]

        if(holder.javaClass == EnviadoViewHolder::class.java){

            val ViewHolder = holder as EnviadoViewHolder
            holder.mensajeEnviado.text = mensajeActual.mensaje

        }else{
            val ViewHolder = holder as RecibidoViewHolder
            holder.mensajeRecibido.text = mensajeActual.mensaje
        }
    }

}