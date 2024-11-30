package com.example.uf1_proyecto.models

import com.google.gson.annotations.SerializedName

data class PeliculaModel (
    @SerializedName("id")
    var id:String,
    @SerializedName("title")
    var title:String,
    @SerializedName("overview")
    var sinopsis:String,
    @SerializedName("poster_path")
    var caratula:String,
    @SerializedName("popularity")
    var popularidad:String,
    @SerializedName("vote_average")
    var puntuacion:String
    )