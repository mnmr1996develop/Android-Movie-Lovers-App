package com.michaelrichards.movieloversapp.repositories.implementaions

import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.model.Movie
import com.michaelrichards.movieloversapp.network.MovieAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiState
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState

private const val TAG = "MovieRepositoryImpl"

class MovieRepositoryImpl(
    private val movieAPI: MovieAPI
) : MovieRepository {

    override suspend fun searchMovieByName(movieName: String): Result<Movie> {
        return try {
            val movieList = movieAPI.searchMovies(movieName)
            Result.success(movieList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchFullMovie(imbdId: String): ApiSuccessFailState<FullMovie> {
        return try {
            val movieList = movieAPI.searchFullMovies(imbdId)
            ApiSuccessFailState.Success(movieList)
        } catch (e: Exception) {
            ApiSuccessFailState.BadRequest()
        }
    }
}