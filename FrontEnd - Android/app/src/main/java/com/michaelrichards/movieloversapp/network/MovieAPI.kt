package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.model.Movie
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET
    suspend fun searchMovies(@Query("q") query: String) : List<Movie>
}