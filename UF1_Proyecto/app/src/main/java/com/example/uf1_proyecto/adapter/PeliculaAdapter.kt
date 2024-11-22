package com.example.uf1_proyecto.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uf1_proyecto.Pelicula
import com.example.uf1_proyecto.R

class PeliculaAdapter(private val peliculaList: List<Pelicula>, private val onClickListener: (Pelicula) -> Unit) : RecyclerView.Adapter<PeliculaViewHolder>(){

    /*
    * Le va a devolver al viewHolder el item/layout, que va a poder modificar*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeliculaViewHolder(layoutInflater.inflate(R.layout.item_pelicula, parent, false))
    }

    /*
    * Devolvemos el tamaño de la lista, para que se puedan cargar todos */
    override fun getItemCount(): Int = peliculaList.size

    /*Este metodo hará una llamada a la función render del viewholder, por cada item de la lista */
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
       val item = peliculaList[position]
        holder.render(item, onClickListener)
    }
}