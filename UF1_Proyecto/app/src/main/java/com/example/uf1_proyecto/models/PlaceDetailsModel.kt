package com.example.uf1_proyecto.models

import com.google.gson.annotations.SerializedName

data class PlaceDetailsModel(
    @SerializedName("place_id")
    var placeId: String,
    @SerializedName("name")
    var placeName: String,
    @SerializedName("geometry")
    var placeLocation: LocationModel
)
