package com.example.uf1_proyecto.network.response

import com.example.uf1_proyecto.models.PeliculaModel
import com.google.gson.annotations.SerializedName

data class PeliculasResponse(
    @SerializedName("results")
    var results: List<PeliculaModel>
)
