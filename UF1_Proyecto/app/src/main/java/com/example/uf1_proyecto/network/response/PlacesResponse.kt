package com.example.uf1_proyecto.network.response

import com.example.uf1_proyecto.models.PlaceDetailsModel
import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("results")
    var results :List<PlaceDetailsModel>
)
