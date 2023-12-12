package com.michaelrichards.movieloversapp.screens.CommentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.dtos.CommentDataDTO
import com.michaelrichards.movieloversapp.dtos.CommentInitializerDTO
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val userMovieReviewsRepository: UserMovieReviewsRepository
) : ViewModel() {

    private val _comments = MutableStateFlow(listOf<CommentDataDTO>())
    val comments: StateFlow<List<CommentDataDTO>> = _comments

    fun loadComments(movieReviewId: Long) {
        viewModelScope.launch {
            when (val potentialComments = userMovieReviewsRepository.getComments(movieReviewId)) {
                is ApiSuccessFailState.Success -> potentialComments.data?.let { _comments.update { it } }
                else -> {}
            }
        }
    }

    fun addComment(movieReviewId: Long, comment: String) {


        if (comment.isNotEmpty()) {

            val commentInitializerDTO = CommentInitializerDTO(comment)

            viewModelScope.launch {
                when (val potentialComments =
                    userMovieReviewsRepository.addComment(movieReviewId, commentInitializerDTO)) {
                    is ApiSuccessFailState.Success -> loadComments(movieReviewId)
                    else -> {

                    }
                }
            }
        }
    }

}