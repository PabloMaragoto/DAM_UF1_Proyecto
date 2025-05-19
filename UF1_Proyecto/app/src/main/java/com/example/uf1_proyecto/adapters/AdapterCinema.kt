package com.example.uf1_proyecto.adapters

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.models.PlaceDetailsModel

class AdapterCinema(val context: Context, var cinemasList: List<PlaceDetailsModel>) :
    RecyclerView.Adapter<AdapterCinema.ViewHolder>() {
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val cvCinema = itemView.findViewById<CardView>(R.id.cvCinema)
            val ivImageCinema = itemView.findViewById<ImageView>(R.id.ivImageCinema)
            val tvNombreCinema = itemView.findViewById<TextView>(R.id.tvNombreCinema)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cinema, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cinemasList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cinema = cinemasList[position]
        holder.tvNombreCinema.text = cinema.placeName

        val photoReference = cinema.photos?.firstOrNull()?.photoReference

        /*val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val halfWidth = screenWidth / 2*/

        if(photoReference != null){
            val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                    "?maxwidth=400" +
                    "&photoreference=$photoReference" +
                    "&key=" + context.getString(R.string.google_maps_key)

            Glide.with(context)
                .load(photoUrl)
                .placeholder(R.drawable.cinema01)
                .into(holder.ivImageCinema)
        }else{
            holder.ivImageCinema.setImageResource(R.drawable.cinema01)
        }
    }
}