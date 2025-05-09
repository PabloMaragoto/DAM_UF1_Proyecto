package com.example.uf1_proyecto.network

import com.example.uf1_proyecto.network.response.PlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesService {

    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("keyword") keyword: String,
        @Query("location") location: String,
        @Query("radius") radius: Number,
        @Query("type") type: String,
        @Query("key") apiKey: String,
    ): Response<PlacesResponse>

    //TODO: REVISAR
    //https://developers.google.com/maps/documentation/places/web-service/legacy/search-nearby?hl=es-419#keyword
    //https://www.digitalocean.com/community/tutorials/google-places-api
}