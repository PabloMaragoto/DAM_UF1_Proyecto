package com.example.uf1_proyecto.models

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("lat")
    var latitude : Number,
    @SerializedName("lng")
    var longitude: Number
)
