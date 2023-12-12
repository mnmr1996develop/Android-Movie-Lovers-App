package com.michaelrichards.movieloversapp.repositories.implementaions

import android.content.SharedPreferences
import android.util.Log
import com.michaelrichards.movieloversapp.dtos.CommentDataDTO
import com.michaelrichards.movieloversapp.dtos.CommentInitializerDTO
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.dtos.ReviewMovieDTO
import com.michaelrichards.movieloversapp.network.MovieReviewAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState

private const val TAG = "UserMovieReviewsRepository"

class UserMovieReviewsRepositoryImpl(
    private val movieReviewAPI: MovieReviewAPI,
    private val prefs: SharedPreferences
) : UserMovieReviewsRepository {


    override suspend fun getUserReviews(username: String): ApiSuccessFailState<List<MovieReviewDataDTO>> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return ApiSuccessFailState.BadRequest(error = null)

        return try {
            ApiSuccessFailState.Success(movieReviewAPI.getUserReviews(token = jwtToken, username = username))

        } catch (e: Exception) {
            ApiSuccessFailState.BadRequest(error = e)
        }
    }

    override suspend fun getReviewsOfFollowing(): ApiSuccessFailState<MutableList<MovieReviewDataDTO>> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return ApiSuccessFailState.BadRequest(error = null)

        return try {
            ApiSuccessFailState.Success(movieReviewAPI.getReviewsOfFollowing(token = jwtToken))

        } catch (e: Exception) {
            ApiSuccessFailState.BadRequest(error = e)
        }

    }

    override suspend fun addReview(review: ReviewMovieDTO): Result<MovieReviewDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        return try {
            Result.success(movieReviewAPI.addReview(jwtToken, review))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getComments(
        movieReviewId: Long,
    ): ApiSuccessFailState<List<CommentDataDTO>>{
        val jwtToken = prefs.getString("jwt", null)
            ?: return ApiSuccessFailState.BadRequest()

        return try {
            ApiSuccessFailState.Success(movieReviewAPI.getPostComment(jwtToken, movieReviewId))
        } catch (e: Exception) {
            ApiSuccessFailState.BadRequest()
        }
    }

    override suspend fun addComment(
        movieReviewId: Long,
        commentInitializerDTO: CommentInitializerDTO
    ) : ApiSuccessFailState<CommentDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return ApiSuccessFailState.BadRequest()

        return try {
            ApiSuccessFailState.Success(movieReviewAPI.addCommentToPost(jwtToken, movieReviewId, commentInitializerDTO))
        } catch (e: Exception) {
            ApiSuccessFailState.BadRequest()
        }
    }

    override suspend fun downVoteReview(reviewId: Long): Result<MovieReviewDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(Exception("Token Invalid"))

        return try {
            Result.success( movieReviewAPI.downVoteReview(jwtToken, reviewId))
        }catch (e: Exception){
            Result.failure(e)
        }


    }

    override suspend fun upVoteReview(reviewId: Long): Result<MovieReviewDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(Exception("Token Invalid"))

        return try {
            Result.success( movieReviewAPI.upVoteReview(jwtToken, reviewId))
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteReview(reviewId: Long): Result<MovieReviewDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(Exception("Token Invalid"))

        return try {
            Result.success( movieReviewAPI.deleteReview(jwtToken, reviewId))
        }catch (e: Exception){
            Result.failure(e)
        }
    }


}