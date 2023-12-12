package com.michaelrichards.movieloversapp.network


import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MovieAPI {
    @GET("/")
    suspend fun searchMovies(@Query("q") query: String): Movie

    @GET("/")
    suspend fun searchFullMovies(@Query("tt") query: String): FullMovie
}