package com.michaelrichards.movieloversapp.screens.HomeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userMovieReviewsRepository: UserMovieReviewsRepository
) : ViewModel() {

    var state = State(loading = false)
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    val data: MutableState<DataOrException<UserProfileDTO, Boolean, Exception>> = mutableStateOf(
        DataOrException(data = null, loading = false)
    )

    private var _movieReviews: MutableStateFlow<ApiSuccessFailState<MutableList<MovieReviewDataDTO>>> = MutableStateFlow( ApiSuccessFailState.Loading())
    var movieReviews : StateFlow<ApiSuccessFailState<MutableList<MovieReviewDataDTO>>> = _movieReviews

    init {
        getUserDetails()
    }

    fun logout() = viewModelScope.launch {
        state.loading = true
        val result = authRepository.logout()
        resultChannel.send(result)
        state.loading = false
    }

    fun getReviewsOfFriends(){
        viewModelScope.launch {
            _movieReviews.update { userMovieReviewsRepository.getReviewsOfFollowing() }
        }
    }

    private fun getUserDetails(): Unit {
        viewModelScope.launch {
            data.value.loading = true
            data.value = userRepository.getUserDetails()
        }
    }

    fun upVoteReview(movieReviewDataDTO: MovieReviewDataDTO) {
        viewModelScope.launch {
            userMovieReviewsRepository.upVoteReview(movieReviewDataDTO.reviewId)
        }
    }

    fun downVoteReview(movieReviewDataDTO: MovieReviewDataDTO){
        viewModelScope.launch {
            userMovieReviewsRepository.downVoteReview(movieReviewDataDTO.reviewId)
        }
    }

    fun deleteReview(movieReview: MovieReviewDataDTO) {
        viewModelScope.launch {
            userMovieReviewsRepository.deleteReview(movieReview.reviewId)

            val items  = _movieReviews.value.data?.apply {
                remove(movieReview)
            }

            _movieReviews.update { ApiSuccessFailState.Success(items) }


        }



    }
}