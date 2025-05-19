package com.example.uf1_proyecto.models

import com.google.gson.annotations.SerializedName

data class PlaceDetailsModel(
    @SerializedName("place_id")
    var placeId: String,
    @SerializedName("name")
    var placeName: String,
    @SerializedName("geometry")
    var placeLocation: Geometry,
    @SerializedName("photos")
    var photos: List<PhotoPlace>? = null
)

data class Geometry (
    @SerializedName("location")
    var location: LocationModel
)

data class PhotoPlace(
    @SerializedName("photo_reference")
    val photoReference: String
)
