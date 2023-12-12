package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.model.Movie
import com.michaelrichards.movieloversapp.repositories.results.ApiState
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState

interface MovieRepository {

    suspend fun searchMovieByName(movieName: String): Result<Movie>

    suspend fun searchFullMovie(imbdId: String): ApiSuccessFailState<FullMovie>
}