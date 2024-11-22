package com.example.uf1_proyecto.adapter

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uf1_proyecto.Pelicula
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.databinding.ItemPeliculaBinding

class PeliculaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemPeliculaBinding.bind(view)

    val caratula = view.findViewById<ImageView>(R.id.caratulaPelicula)

    /*
    * Esta función será llamada por cada item dentro de la lista*/
    fun render(peliculaModel: Pelicula, onClickListener: (Pelicula) -> Unit) {
        binding.txtTitulo.text = peliculaModel.titulo
        binding.txtAnho.text = peliculaModel.anho
        binding.txtDirector.text = peliculaModel.director

        /*Ponemos un listener en toda la vista del item*/
        itemView.setOnClickListener {
            onClickListener(peliculaModel)
        }
    }


}