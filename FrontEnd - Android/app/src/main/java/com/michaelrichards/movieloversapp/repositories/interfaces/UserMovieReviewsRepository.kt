package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.dtos.CommentDataDTO
import com.michaelrichards.movieloversapp.dtos.CommentInitializerDTO
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.dtos.ReviewMovieDTO
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState


interface UserMovieReviewsRepository {

    suspend fun getUserReviews(username: String): ApiSuccessFailState<List<MovieReviewDataDTO>>

    suspend fun getReviewsOfFollowing(): ApiSuccessFailState<MutableList<MovieReviewDataDTO>>

    suspend fun addReview(review: ReviewMovieDTO): Result<MovieReviewDataDTO>

    suspend fun getComments(movieReviewId: Long): ApiSuccessFailState<List<CommentDataDTO>>

    suspend fun addComment(movieReviewId: Long, commentInitializerDTO: CommentInitializerDTO): ApiSuccessFailState<CommentDataDTO>

    suspend fun downVoteReview(reviewId: Long): Result<MovieReviewDataDTO>

    suspend fun upVoteReview(reviewId: Long): Result<MovieReviewDataDTO>

    suspend fun deleteReview(reviewId: Long): Result<MovieReviewDataDTO>
}