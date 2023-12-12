package com.michaelrichards.movieloversapp.screens.UserDetailsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO

import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiState
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserDetailsViewModel"




@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userMovieReviewsRepository: UserMovieReviewsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<ApiState<UserProfileDTO>>(ApiState.Loading())
    val user: StateFlow<ApiState<UserProfileDTO>> = _user



    private val _logedIN = MutableStateFlow<AuthResult<Unit>>(AuthResult.Authorized())
    val logedIN: StateFlow<AuthResult<Unit>> = _logedIN

    private var _movieReviews: MutableStateFlow<ApiSuccessFailState<List<MovieReviewDataDTO>>> = MutableStateFlow( ApiSuccessFailState.Loading())
    val movieReviews : StateFlow<ApiSuccessFailState<List<MovieReviewDataDTO>>> = _movieReviews



    fun getUserProfileDetails(username: String)  {
        viewModelScope.launch {
            val response = userRepository.getUserProfile(username)
            _user.update { response }

            val movieReviewResponse = userMovieReviewsRepository.getUserReviews(username)
            _movieReviews.update { movieReviewResponse }

        }
    }

    fun loadCommentsForPost(postId: Long){
        viewModelScope.launch {
            userMovieReviewsRepository.getComments(postId)
        }
    }

    fun logout(){
        viewModelScope.launch {
            _logedIN.value = authRepository.logout()
        }

    }

    private fun loadMovieReviews(username: String){
        viewModelScope.launch {
            _movieReviews.value = userMovieReviewsRepository.getUserReviews(username)
        }
    }





}