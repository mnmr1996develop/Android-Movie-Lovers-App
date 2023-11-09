package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.model.Movie

interface MovieRepository {

    suspend fun searchMovieByName(movieName: String): Result<Movie>

}