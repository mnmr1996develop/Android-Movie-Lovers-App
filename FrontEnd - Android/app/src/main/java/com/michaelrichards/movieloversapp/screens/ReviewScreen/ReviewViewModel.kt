package com.michaelrichards.movieloversapp.screens.ReviewScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.dtos.ReviewMovieDTO
import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val reviewsRepository: UserMovieReviewsRepository
) : ViewModel(){

    private val _movieDetails = MutableStateFlow<ApiSuccessFailState<FullMovie>>(ApiSuccessFailState.Loading())
    val movieDetails: StateFlow<ApiSuccessFailState<FullMovie>> = _movieDetails

    private var _reviewSuccess = MutableStateFlow(false)
    val reviewSuccess : StateFlow<Boolean> = _reviewSuccess.asStateFlow()

    fun getMovieDetails(imbdId: String) = viewModelScope.launch {
        val details  = movieRepository.searchFullMovie(imbdId)
        _movieDetails.value = details
    }

    fun reviewMovie(reviewMovieDTO: ReviewMovieDTO) {
        viewModelScope.launch {
            val reviewAPISend = reviewsRepository.addReview(reviewMovieDTO)
            if (reviewAPISend.isSuccess)
                _reviewSuccess.update { true }
        }
    }

}