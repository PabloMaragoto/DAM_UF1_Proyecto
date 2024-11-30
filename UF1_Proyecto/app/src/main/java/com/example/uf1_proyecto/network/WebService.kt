package com.example.uf1_proyecto.network

import com.example.uf1_proyecto.network.response.PeliculasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("search/movie")
    suspend fun getParasite(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<PeliculasResponse>


}