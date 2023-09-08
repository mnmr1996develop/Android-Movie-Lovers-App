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

        return movieReviewRepository.save(movieReview)
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
        var cumRating = 0f
        for (review in reviews){
            cumRating+= review.rating
        }
        return if (reviews.size == 0)
             0f
        else
            cumRating/reviews.size

    }
}