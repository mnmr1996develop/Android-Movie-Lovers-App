package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.MovieReviewDTO
import com.MichaelRichards.MovieLovers.exceptions.CustomExceptions
import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.repositories.MovieReviewRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class MovieReviewService (
    private val enthusiastService: EnthusiastService,
    private val movieReviewRepository: MovieReviewRepository
){

    fun findById(id: Long): MovieReview = movieReviewRepository.findById(id).orElseThrow { Exception("") }

    fun addReview(bearerToken: String, review: MovieReviewDTO) : MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        for (userReview in user.movieReviews){
            if (userReview.imdbId == review.imdbId){
                throw CustomExceptions.DuplicateReviewException("Cannot Review A Movie Twice")
            }
        }

        val movieReview = MovieReview(
            imdbId = review.imdbId,
            enthusiast = user,
            description = review.description,
            rating = review.rating,
            createdAt = LocalDateTime.now(),
            lastUpdated = LocalDateTime.now()
        )
        user.addReview(movieReview)

        enthusiastService.save(user)

        return movieReview
    }

    fun updateReview(bearerToken: String, review: MovieReviewDTO): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        for (userReview in user.movieReviews){
            if (userReview.imdbId == review.imdbId){
                userReview.description = review.description
                userReview.lastUpdated = LocalDateTime.now()
                userReview.rating = review.rating
                return movieReviewRepository.save(userReview)
            }
        }
        throw CustomExceptions.InvalidReview("Review_Not_Found")
    }

    fun deleteReview(bearerToken: String, imdbId: String): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        for (userReview in user.movieReviews){
            if (userReview.imdbId == imdbId){
                user.deleteReview(userReview)
            }
        }
        throw CustomExceptions.InvalidReview("Review_Not_Found")
    }

    fun getMovieAverage(imdbName: String): Float{
        val reviews = movieReviewRepository.findByImdbId(imdbName)
        if (reviews.isEmpty()) return 0f

        var cumRating = 0f

        for (review in reviews){
            cumRating+= review.rating
        }

        return cumRating/reviews.size
    }

    fun getMyMovieReviews(bearerToken: String): List<MovieReview> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        return user.movieReviews
    }

    fun upVoteReview(bearerToken: String, movieReviewId: Long): MovieReview {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = findById(movieReviewId)
        movieReview.upVote(user)
        return movieReviewRepository.save(movieReview)
    }

    fun downVoteReview(bearerToken: String, movieReviewId: Long): MovieReview {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = findById(movieReviewId)
        movieReview.downVote(user)
        return movieReviewRepository.save(movieReview)
    }
}