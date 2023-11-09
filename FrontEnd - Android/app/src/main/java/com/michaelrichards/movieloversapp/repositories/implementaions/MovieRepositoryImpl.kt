package com.michaelrichards.movieloversapp.repositories.implementaions

import com.michaelrichards.movieloversapp.model.Movie
import com.michaelrichards.movieloversapp.network.MovieAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository

private const val TAG = "MovieRepositoryImpl"

class MovieRepositoryImpl(
    private val movieAPI: MovieAPI
) : MovieRepository {

    override suspend fun searchMovieByName(movieName: String): Result<Movie> {
        return try {
            val movieList = movieAPI.searchMovies(movieName)
            Result.success(movieList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}