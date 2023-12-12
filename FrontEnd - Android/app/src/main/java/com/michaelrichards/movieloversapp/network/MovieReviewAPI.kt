package com.michaelrichards.movieloversapp.network


import com.michaelrichards.movieloversapp.dtos.CommentDataDTO
import com.michaelrichards.movieloversapp.dtos.CommentInitializerDTO
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.dtos.ReviewMovieDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


/*
* Equivalent to the Movie Review Controller
* */
@Singleton
interface MovieReviewAPI {

    @POST("review")
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Body reviewMovieDTO: ReviewMovieDTO
    ): MovieReviewDataDTO

    @PATCH("review")
    suspend fun updateReview(
        @Header("Authorization") token: String,
        @Body movieReviewDataDTO: MovieReviewDataDTO
    ): MovieReviewDataDTO


    @DELETE("review")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Query("movieReviewId") movieReviewId: Long
    ): MovieReviewDataDTO


    @GET("review/by/{username}")
    suspend fun getUserReviews(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): List<MovieReviewDataDTO>

    @GET("review/followingReviews")
    suspend fun getReviewsOfFollowing(
        @Header("Authorization") token: String,
    ): MutableList<MovieReviewDataDTO>

    @GET("review/comments/{movieReviewId}")
    suspend fun getPostComment(
        @Header("Authorization") token: String,
        @Path("movieReviewId") movieReviewId: Long,
    ): List<CommentDataDTO>


    @POST("review/comments/{movieReviewId}")
    suspend fun addCommentToPost(
        @Header("Authorization") token: String,
        @Path("movieReviewId") movieReviewId: Long,
        @Body comment: CommentInitializerDTO
    ): CommentDataDTO

    @POST("review/downVote/{reviewId}")
    suspend fun downVoteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Long
    ): MovieReviewDataDTO

    @POST("review/upVote/{reviewId}")
    suspend fun upVoteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Long
    ): MovieReviewDataDTO




}