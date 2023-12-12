package com.michaelrichards.movieloversapp.screens.MovieDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<ApiSuccessFailState<FullMovie>>(ApiSuccessFailState.Loading())
    val movieDetails: StateFlow<ApiSuccessFailState<FullMovie>> = _movieDetails

    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        val details  = movieRepository.searchFullMovie(imdbId)
        _movieDetails.value = details
    }

}