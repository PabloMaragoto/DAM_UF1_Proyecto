package com.example.uf1_proyecto.network

import com.example.uf1_proyecto.core.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val webService: WebService by lazy {
        val retrofit = Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        retrofit.create(WebService::class.java)
    }

    val googlePlacesService: GooglePlacesService by lazy {
        val retrofit = Retrofit.Builder().baseUrl(Constants.API_URL_PLACES)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        retrofit.create(GooglePlacesService::class.java)
    }
}