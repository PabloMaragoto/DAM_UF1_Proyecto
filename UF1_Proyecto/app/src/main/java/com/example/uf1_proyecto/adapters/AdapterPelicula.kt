package com.example.uf1_proyecto.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.models.PeliculaModel
import com.example.uf1_proyecto.views.HomeFragmentDirections


class AdapterPelicula(
    //Contexto de la alicación que nos permite saber de donde vienen todas las peticiones
    val context: Context,
    var peliculasList: List<PeliculaModel>

) : RecyclerView.Adapter<AdapterPelicula.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvPelicula = itemView.findViewById<CardView>(R.id.cvMovie)
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvInfo = itemView.findViewById<TextView>(R.id.tvInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peliculasList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = peliculasList[position]

        Glide.with(context)
            .load("${Constants.API_URL_IMAGE}${pelicula.caratula}")
            .apply(RequestOptions().override(450, 330))
            .into(holder.ivImage)

        holder.tvInfo.text =
            HtmlCompat.fromHtml("<b>Title:</b> " + pelicula.title +
                                "<br><b>Popularity</b>" + pelicula.popularidad +
                                "<br><b>Rating:</b>" + pelicula.puntuacion,
                                HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.cvPelicula.setOnClickListener{ view ->
           val action = HomeFragmentDirections.actionHomeFragmentToPeliculaInfoFragment(
               pelicula.title,
               pelicula.sinopsis,
               pelicula.caratula,
               pelicula.id
           )
            view.findNavController().navigate(action)//showSinopsis(pelicula.title, pelicula.sinopsis)
        }
    }

    private fun showSinopsis(titulo: String, sinopsis:String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titulo);
        builder.setMessage(sinopsis)
        builder.show()
    }


}